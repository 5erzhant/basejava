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
        sqlHelper.execute("DELETE FROM resume", ps -> {
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
            do {
                r.addContact(ContactType.valueOf(rs.getString("type")),
                        rs.getString("value") == null ? "" : rs.getString("value"));
            } while (rs.next());
            return r;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        delete(r.getUuid());
        save(r);
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?,?)")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> entrySet : r.getContacts().entrySet()) {
                    ps.setString(1, entrySet.getValue());
                    ps.setString(2, r.getUuid());
                    ps.setString(3, entrySet.getKey().name());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
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
                Resume r = get(rs.getString("uuid").trim());
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
}