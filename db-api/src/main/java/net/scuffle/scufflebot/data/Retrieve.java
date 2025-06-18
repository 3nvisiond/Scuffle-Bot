package net.scuffle.scufflebot.data;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Properties;

/**
 * A utility based class designed for simply the process of transaction read input.
 */
public abstract class Retrieve {
    private static final Logger LOG = LoggerFactory.getLogger(Retrieve.class.getName());

    /**
     * Creates a new statement to the database given.
     *
     * @param connection The connection to the database
     * @return The {@link Statement} created with the given connection
     * @throws SQLException If the {@link Statement} could not be made
     */
    public static Statement createStatement(@NotNull Connection connection) throws SQLException {
        return connection.createStatement();
    }

    /**
     * Creates a new statement to the database given and decides whether to let Postgres handle transactions.
     *
     * @param connection The connection to the database
     * @param autoCommit Determines whether to let Postgres automatically handle transactions
     * @return The new {@link Statement} created with the given connection
     * @throws SQLException If the {@link Statement} could not be made
     */
    public static Statement createStatement(@NotNull Connection connection, boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);

        return createStatement(connection);
    }

    /**
     *
     *
     * @param connection The connection to the database
     * @param autoCommit Determines whether to let Postgres automatically handle transactions
     * @param fetchSize The amount of rows to retrieve from in each network call
     * @param context The SQL command to automatically prepare when firing
     * @return The {@link Statement} created with the given connection
     * @throws SQLException If the {@link Statement} could not be made
     *
     */
    public static Statement createStatement(
            @NotNull Connection connection,
            boolean autoCommit,
            int fetchSize,
            String context
    ) throws SQLException {
        connection.setAutoCommit(autoCommit);

        return connection.prepareStatement(context);
    }
}
