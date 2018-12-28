package be.wouterversyck.slackintegration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FunFactNotFoundException extends ResponseStatusException {
    public FunFactNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
