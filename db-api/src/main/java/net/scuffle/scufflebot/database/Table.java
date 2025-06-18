package net.scuffle.scufflebot.database;

import org.jetbrains.annotations.Nullable;

import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface Table {
    /**
     *
     *
     * @param name The name of the table
     * @param properties the contents of the table if any
     * @param notNull The numerical position where
     * @return
     */
    boolean create(String name, List<Map<String, Types>> properties, int[] notNull);

    /**
     * Creates a new table where the primary key is given.
     * Instances the same as calling SQL's
     *
     *
     * @param name The name of the table
     * @param properties
     * @param primaryKey
     * @param notNull
     * @return
     */
    boolean create(String name, List<Map<String, Types>> properties, int primaryKey, int[] notNull);

    /**
     *
     * @param name
     * @param properties
     * @param from
     * @param notNull
     * @return
     */
    boolean create(String name, List<Map<String, Types>> properties, List<Map<String, Types>> from, int[] notNull);

    /**
     *
     * @param name The name of the table
     * @param properties
     * @param from
     * @param primary
     * @param notNull
     * @return
     */
    boolean create(String name, List<Map<String, Types>> properties, List<Map<String, Types>> from, int primary, int[] notNull);

    /**
     * Changes the set table name to the specified new name.
     * All table data persists and will not be erased.
     *
     * @param name The original name of the table
     * @param newName The value to set the table name to
     * @return Whether the table's name was successfully changed
     */
    boolean alter(String name, String newName);

    /**
     * Adds a new column to the table.
     *
     * @param name The name of the table
     * @param column The data to the table
     * @return
     */
    boolean alter(String name, Map<String, Types> column);

    /**
     *
     * @param name The name of the table
     * @param column The data to
     * @param drop Determines whether the table should be dropped upon
     * @return
     */
    boolean alter(String name, Map<String, Types> column, boolean drop);

    /**
     *
     * @param name
     * @param column
     * @return
     */
    boolean modify(String name, Map<String, Types> column);

    /**
     *
     * @param name The name of the table
     * @return
     */
    boolean drop(String name);

    /**
     *
     * @param name
     * @return
     */
    boolean truncate(String name);

    /**
     *
     * @param name
     * @param force
     * @return
     */
    boolean drop(String name, boolean force);

    /**
     *
     * @param name
     * @param moveData
     * @param data
     * @return
     */
    boolean drop(String name, boolean moveData, @Nullable List<Map<String, Types>> data);

    /**
     *
     * @param name
     * @return
     */
    int size(String name);

    /**
     *
     * @param name
     * @param selection
     * @return
     */
    List<Map<String, Types>> selectFrom(String name, String selection);

    /**
     *
     * @param columnName
     * @return
     */
    Map<String, Types> selectFromColumn(String columnName);

    /**
     * Returns a lowercase string given content.
     *
     * @param content The string to be turned to lowercase
     * @return The given string in lowercase
     */
    String toLowercase(String content);

    /**
     * Checks if the provided value is null.
     *
     * @param key The column to check for
     * @param value The value to check if it is NULL or not
     * @return False if it is not null, true if it is
     */
    boolean isNull(String key, Types value);

    /**
     * Checks if the table is empty
     *
     * @return True if the table is empty
     */
    boolean isEmpty();

    /**
     * Converts a SQL Table to JSON either for debugging or viewing purposes.
     *
     * @see Table
     */
    void toJSON();

    @Override
    String toString();
}
