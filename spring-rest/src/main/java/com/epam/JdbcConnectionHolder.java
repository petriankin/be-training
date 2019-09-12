package com.epam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectionHolder {

    private DataSource dataSource;
    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public JdbcConnectionHolder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnectionWithNoAutoCommit() throws SQLException {
        if (connectionThreadLocal.get() == null
                    || connectionThreadLocal.get().isClosed()) {
                Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connectionThreadLocal.set(connection);
        }
        return connectionThreadLocal.get();
    }

    public Connection getConnectionWithAutoCommit() throws SQLException {
        if (connectionThreadLocal.get() == null
                || connectionThreadLocal.get().isClosed()) {
            connectionThreadLocal.set(dataSource.getConnection());
        }
        return connectionThreadLocal.get();
    }

    public void commit() {
        try {
            getConnectionWithNoAutoCommit().commit();
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
//        Connection connection = this.connectionThreadLocal.get();
//        if (connection != null) {
//            try {
//                connection.commit();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    public void rollback() {
        try {
            getConnectionWithNoAutoCommit().rollback();
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
//        Connection connection = this.connectionThreadLocal.get();
//        if (connection != null) {
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
}
