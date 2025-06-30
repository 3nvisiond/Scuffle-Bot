package net.scuffle.scufflebot.entity.level;

public class LevelWithdrawException extends LevelException {
    public LevelWithdrawException(String message, InterruptedException exception) {
        super(message, exception);
    }

    public LevelWithdrawException(InterruptedException exception) {
        super(exception);
    }

    public LevelWithdrawException(String message) {
        super(message);
    }

    public LevelWithdrawException(String message, InterruptedException exception, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, exception, enableSuppression, writableStackTrace);
    }
}
