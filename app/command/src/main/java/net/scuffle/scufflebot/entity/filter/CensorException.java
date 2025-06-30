package net.scuffle.scufflebot.entity.filter;

public class CensorException extends RuntimeException {
    public CensorException(String message, InterruptedException exception) {
        super(message, exception);
    }

    public CensorException(String message) {
      super(message);
    }

    public CensorException(String message, InterruptedException exception, boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, exception, enableSuppression, writableStackTrace);
    }
}
