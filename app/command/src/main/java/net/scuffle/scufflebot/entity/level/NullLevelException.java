package net.scuffle.scufflebot.entity.level;

public class NullLevelException extends LevelException {
    public NullLevelException(String message, InterruptedException exception) {
        super(message, exception);
    }

    public NullLevelException(InterruptedException exception) {
        super(exception);
    }

    public NullLevelException(String message) {
        super(message);
    }

    public NullLevelException(String message, InterruptedException exception, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, exception, enableSuppression, writableStackTrace);
    }
}
