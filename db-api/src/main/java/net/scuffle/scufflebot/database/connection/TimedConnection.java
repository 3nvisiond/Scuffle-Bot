package net.scuffle.scufflebot.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TimedConnection implements Network {
    @Override
    public Connection connect(
            String dbName,
            String user,
            String pass,
            int port,
            boolean sshEnabled
    ) throws SQLException {
        final String URL = String.format("jbdc:postgresql://localhost:%d/%s?user=%s&password=%s&ssl=%b",
                port, dbName, user, pass, sshEnabled);

        return DriverManager.getConnection(URL);
    }

    @Override
    public Connection connect(
            String dbName,
            String user,
            String pass,
            int port,
            Properties properties
    ) throws SQLException {
        final String URL = String.format("jbdc:postgresql://localhost:%d/%s?user=%s&password=%s",
                port, dbName, user, pass);

        return DriverManager.getConnection(URL, properties);
    }

    public Connection timedConnection(
            String dbName,
            String user,
            String pass,
            int port,
            Properties properties,
            long connectionTimeout,
            long idleTimeout,
            long lifetime
    ) throws SQLException {
        final String URL = String.format("jbdc:postgresql://localhost:%d/%s?user=%s&password=%s",
                port, dbName, user, pass);



        return DriverManager.getConnection(URL, properties);
    }
}
