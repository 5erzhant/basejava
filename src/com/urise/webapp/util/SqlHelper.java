package com.urise.webapp.util;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String request, ABlockOfCode<T> aBlockOfCode) {
        T t;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(request)) {
            t = aBlockOfCode.doAction(ps);
        } catch (SQLException e) {
            String state = e.getSQLState();
            if (state.equals("23505")) {
                throw new ExistStorageException();
            }
            throw new StorageException(e);
        }
        return t;
    }
}