package net.scuffle.scufflebot.database;

import java.util.List;

public interface View {
    boolean createView(String view, List<String> selection, String from, final String SQL_WHERE_CONDITION);
}
