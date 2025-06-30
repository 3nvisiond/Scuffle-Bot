package net.scuffle.scufflebot.entity.level;

import net.dv8tion.jda.api.entities.Member;

public class LevelBuilder extends LevelHandler {
    public Member getMember() {
        return null;
    }

    @Override
    public void setProgressionRate(ProgressionRate rate) {
        // do sum
    }

    @Override
    public ProgressionRate getProgressionRate() {
        // do sum

        return ProgressionRate.NORMAL;
    }

    @Override
    public float getLevel(String message, int level) {
        return 0;
    }

    @Override
    public void setLevel(int level) {

    }

    @Override
    public Member getAssignedMember(long id) {
        return null;
    }

    @Override
    public long getIdLong() {
        return 0;
    }
}
