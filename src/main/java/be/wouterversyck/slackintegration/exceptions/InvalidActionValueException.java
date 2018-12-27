package be.wouterversyck.slackintegration.exceptions;

public class InvalidActionValueException extends RuntimeException {
    public InvalidActionValueException(String message) {
        super(message);
    }
}
