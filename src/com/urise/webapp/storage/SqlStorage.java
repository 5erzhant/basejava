package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("" + "DELETE FROM section;" +
                "                      DELETE FROM contact;" +
                "                      DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionalExecute(conn -> {
            Resume r = createResume(uuid, conn);
            extractContact(r, conn);
            extractSection(r, conn);
            return r;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionalExecute(conn -> {
            if (addResume(conn, "UPDATE resume SET full_name = ? WHERE uuid = ?", r) == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?");
                 PreparedStatement ps2 = conn.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
                addContact(conn, r);
                ps2.setString(1, r.getUuid());
                ps2.execute();
                addSection(conn, r);
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(conn -> {
            addResume(conn, "INSERT INTO resume (full_name, uuid) VALUES (?,?)", r);
            addContact(conn, r);
            addSection(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM section WHERE resume_uuid = ?;" +
                "                 DELETE FROM contact WHERE resume_uuid = ?;" +
                "                 DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.setString(2, uuid);
            ps.setString(3, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = new ArrayList<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid").trim();
                    list.add(createResume(uuid, conn));
                }
                for (Resume r : list) {
                    extractContact(r, conn);
                    extractSection(r, conn);
                }
            }
            return null;
        });
        return list;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    private Resume createResume(String uuid, Connection conn) throws SQLException {
        String fullName;
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            fullName = rs.getString("full_name");
        }
        return new Resume(uuid, fullName);
    }

    private void extractContact(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String value = rs.getString("value");
                if (value != null) {
                    r.addContact(ContactType.valueOf(rs.getString("type")), value);
                }
            }
        }
    }

    private void extractSection(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AbstractSection as = null;
                SectionType type = SectionType.valueOf(rs.getString("type"));
                String value = rs.getString("value");
                if (value != null) {
                    switch (type) {
                        case PERSONAL, OBJECTIVE -> as = new TextSection(value);
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            ListSection ls = new ListSection();
                            String[] splitValue = value.split("\\n");
                            for (String s : splitValue) {
                                ls.addContent(s);
                            }
                            as = ls;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + type);
                    }
                }
                r.addSection(type, as);
            }
        }
    }

    private int addResume(Connection conn, String request, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            return ps.executeUpdate();
        }
    }

    private void addContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entrySet : r.getContacts().entrySet()) {
                ps.setString(1, entrySet.getValue());
                ps.setString(2, r.getUuid());
                ps.setString(3, entrySet.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (value, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entrySet : r.getSections().entrySet()) {
                if (entrySet.getValue() instanceof TextSection ts) {
                    ps.setString(1, ts.getContent());
                } else if (entrySet.getValue() instanceof ListSection ls) {
                    StringBuilder sb = new StringBuilder();
                    for (String s : ls.getContentList()) {
                        sb.append(s).append("\n");
                    }
                    ps.setString(1, sb.toString());
                }
                ps.setString(2, r.getUuid());
                ps.setString(3, entrySet.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}