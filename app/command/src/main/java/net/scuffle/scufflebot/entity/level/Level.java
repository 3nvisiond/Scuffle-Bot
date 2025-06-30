package net.scuffle.scufflebot.entity.level;

import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;

public interface Level extends ISnowflake {
    float level = 0.0F;

    float getLevel(String message, int level);

    void setLevel(int level);

    Member getAssignedMember(long id);
}
