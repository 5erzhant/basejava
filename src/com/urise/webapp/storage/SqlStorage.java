package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume;" +
                "                 DELETE FROM contact", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("" +
                "SELECT * FROM resume r " +
                "  LEFT JOIN contact c " +
                "    ON r.uuid = c.resume_uuid" +
                " WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            addSqlContact(r, rs);
            return r;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionalExecute(conn -> {
            if (changeResume(conn, "UPDATE resume SET full_name = ? WHERE uuid = ?", r) == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?");
            ps.setString(1, r.getUuid());
            ps.execute();
            insertContact(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(conn -> {
            changeResume(conn, "INSERT INTO resume (full_name, uuid) VALUES (?,?)", r);
            insertContact(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?;" +
                "                 DELETE FROM contact WHERE resume_uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.setString(2, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = new ArrayList<>();
        sqlHelper.transactionalExecute(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ps = conn.prepareStatement("SELECT * FROM resume r INNER JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid = ? ");
                ps.setString(1, rs.getString("uuid"));
                ResultSet resultSet = ps.executeQuery();
                resultSet.next();
                Resume r = new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name"));
                addSqlContact(r, resultSet);
                list.add(r);
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

    private void addSqlContact(Resume r, ResultSet rs) throws SQLException {
        do {
            String value = rs.getString("value");
            if (value != null) {
                r.addContact(ContactType.valueOf(rs.getString("type")), value);
            }
        } while (rs.next());
    }

    private int changeResume(Connection conn, String request, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            return ps.executeUpdate();
        }
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
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
}