package net.scuffle.scufflebot.database.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface Network {
    /**
     * Attempts to connect to the specified database given the username, database name, and password.
     * If the first attempt fails, it will try again three separate consequtive times.
     *
     * @param dbName The name of the database
     * @param user The username
     * @param pass The password
     * @param port The port to connect to the database
     * @param sshEnabled Tells the database whether to allow ssl connections
     * @return The result of the connection
     * @throws SQLException Either the connection could not be made or a wrong value was specified
     */
    Connection connect(String dbName, String user, String pass, int port, boolean sshEnabled) throws SQLException;

    /**
     * Attempts to connect to the specified database given the username, database name, and password.
     *
     * @param dbName The name of the database
     * @param user The username
     * @param pass The password
     * @param port The port to connect to the database
     * @param properties Config properties to give to the database before making the final connection
     * @return The result of the connection
     * @throws SQLException Either the connection could not be made or a wrong value was specified
     */
    Connection connect(String dbName, String user, String pass, int port, Properties properties) throws SQLException;
}
