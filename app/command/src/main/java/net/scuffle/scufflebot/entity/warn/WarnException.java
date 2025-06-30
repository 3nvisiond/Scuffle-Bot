package net.scuffle.scufflebot.entity.warn;

public class WarnException extends RuntimeException {
    public WarnException(String message, InterruptedException exception) {
        super(message, exception);
    }

    public WarnException(InterruptedException exception) {
      super(exception);
    }

    public WarnException(String message) {
      super(message);
    }

    public WarnException(String message, InterruptedException exception, boolean enableSuppression,
                         boolean writableStackTrace) {
      super(message, exception, enableSuppression, writableStackTrace);
    }
}
