package net.scuffle.scufflebot.entity.level;

public abstract class LevelHandler implements Level {
    public abstract void setProgressionRate(ProgressionRate rate);

    public abstract ProgressionRate getProgressionRate();
}
