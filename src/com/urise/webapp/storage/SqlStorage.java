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
        sqlHelper.execute("delete from resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("" +
                "select * from resume r " +
                "  left join contact c " +
                "    on r.uuid = c.resume_uuid" +
                " where r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType contactType = ContactType.valueOf(rs.getString("type"));
                r.addContact(contactType, value);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.transactionalExecute(conn -> {
            if (changeTable("update resume set full_name = ? where uuid = ?",
                    "update contact set value = ? where resume_uuid = ? and type = ?", conn, r) == 0)
                throw new NotExistStorageException(r.getUuid());
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(conn -> {
            changeTable("insert into resume (full_name, uuid) values (?,?)",
                    "insert into contact (value, resume_uuid, type) values (?,?,?)", conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("delete from resume where uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = new ArrayList<>();
        sqlHelper.transactionalExecute(conn -> {
            PreparedStatement ps = conn.prepareStatement("select * from resume order by full_name, uuid");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ps = conn.prepareStatement("select * from resume r inner join contact c on r.uuid = c.resume_uuid " +
                        "where r.uuid = ? ");
                ps.setString(1, rs.getString("uuid"));
                ResultSet resultSet = ps.executeQuery();
                resultSet.next();
                Resume r = new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name"));
                do {
                    r.addContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                } while (resultSet.next());
                list.add(r);
            }
            return null;
        });
        return list;
    }

    @Override
    public int size() {
        return sqlHelper.execute("select count(*) from resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    private int changeTable(String resumeRequest, String contactRequest, Connection conn, Resume r) throws SQLException {
        int executeUpdate;
        try (PreparedStatement ps = conn.prepareStatement(resumeRequest)) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            executeUpdate = ps.executeUpdate();
        }
        try (PreparedStatement ps = conn.prepareStatement(contactRequest)) {
            for (Map.Entry<ContactType, String> entrySet : r.getContacts().entrySet()) {
                ps.setString(1, entrySet.getValue());
                ps.setString(2, r.getUuid());
                ps.setString(3, entrySet.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        return executeUpdate;
    }
}