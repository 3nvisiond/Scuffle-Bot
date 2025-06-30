package net.scuffle.scufflebot;

public class CommandException extends RuntimeException {
    public CommandException(String message, InterruptedException exception) {
        super(message, exception);
    }

    public CommandException(String message) {
      super(message);
    }
}
