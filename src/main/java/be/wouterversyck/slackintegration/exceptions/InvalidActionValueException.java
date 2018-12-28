package be.wouterversyck.slackintegration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static java.lang.String.format;

public class InvalidActionValueException extends ResponseStatusException {
    public InvalidActionValueException(String action) {
        super(HttpStatus.BAD_REQUEST, format("%s is not a valid action", action));
    }
}
