package net.scuffle.scufflebot.entity.level;

public class LevelException extends RuntimeException {
    public LevelException(String message, InterruptedException exception) {
        super(message, exception);
    }

    public LevelException(InterruptedException exception) {
        super(exception);
    }

    public LevelException(String message) {
        super(message);
    }

    public LevelException(String message, InterruptedException exception, boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, exception, enableSuppression, writableStackTrace);
    }
}
