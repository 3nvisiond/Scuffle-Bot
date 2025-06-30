package net.scuffle.scufflebot.entity.level;

public class LevelDepositException extends LevelException {
    public LevelDepositException(String message, InterruptedException exception) {
        super(message, exception);
    }

    public LevelDepositException(InterruptedException exception) {
        super(exception);
    }

    public LevelDepositException(String message) {
        super(message);
    }

    public LevelDepositException(String message, InterruptedException exception, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, exception, enableSuppression, writableStackTrace);
    }
}
