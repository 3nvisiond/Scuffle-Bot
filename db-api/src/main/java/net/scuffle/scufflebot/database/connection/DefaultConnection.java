package net.scuffle.scufflebot.database.connection;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DefaultConnection implements Network {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultConnection.class.getName());

    @Override
    public Connection connect(
            String dbName,
            String user,
            String pass,
            int port,
            boolean sslEnabled
    ) throws SQLException {
        final String URL = String.format("jbdc:postgresql://localhost:%d/%s?user=%s&password=%s&ssl=%b",
                port, dbName, user, pass, sslEnabled);

        return DriverManager.getConnection(URL);
    }

    @Override
    public Connection connect(
            @NotNull String dbName,
            @NotNull String user,
            @NotNull String pass,
            int port,
            @NotNull Properties properties
    ) throws SQLException {
        final String URL = String.format("jbdc:postgresql://localhost:%d/%s?user=%s&password=%s",
                port, dbName, user, pass);

        return DriverManager.getConnection(URL, properties);
    }
}
