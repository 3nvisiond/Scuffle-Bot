package net.scuffle.scufflebot.database.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface TimedNetwork {
    /**
     * A more advanced method compared to the {@link Network#connect(String, String, String, int, boolean)} method.
     * A connection is made with time restrictions used for either debugging or tighter controll over privallges.
     * <P><b>Note:</b> When either of the conditions are met for
     *
     * @param dbName The name of the database
     * @param user The username
     * @param pass The password
     * @param port The port to connect to the database
     * @param properties Config properties to give to the database before making the final connection
     * @param connectionTimeout The time duration the database will wait for before cancelling any connection attempts
     * @param idleTimeout The time duration the database will wait until the user is automatically exitted out of the database
     * @param lifetime The amount of time the connection to the database will stay up for
     * @return The result of the connection
     * @throws SQLException Either the connection could not be made, or a wrong value was specified
     */
    Connection timedConnection(
            String dbName,
            String user,
            String pass,
            int port,
            Properties properties,
            long connectionTimeout,
            long idleTimeout,
            long lifetime
    ) throws SQLException;
}
