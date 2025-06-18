package net.scuffle.scufflebot.database;

import org.jetbrains.annotations.Nullable;

import java.sql.Types;
import java.util.List;
import java.util.Map;

public interface Index {
    boolean index(String indexName, String onTableName, List<String> properties, boolean isUnique);

    boolean drop(String name);

    boolean drop(String name, boolean force);

    boolean drop(String name, boolean force, @Nullable List<Map<String, Types>> data);
}
