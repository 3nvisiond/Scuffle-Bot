package net.scuffle.scufflebot.database;

public interface Database {
    void backupDatabase(String database, String filePath, boolean differential);
}
