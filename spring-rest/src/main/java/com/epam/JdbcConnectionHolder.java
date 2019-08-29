package com.epam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectionHolder {

    public DataSource dataSource;
    public ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public JdbcConnectionHolder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        if (connectionThreadLocal.get() == null
                || connectionThreadLocal.get().isClosed()) {
            connectionThreadLocal.set(dataSource.getConnection());
        }
        return connectionThreadLocal.get();
    }
}
