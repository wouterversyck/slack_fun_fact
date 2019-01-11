package be.wouterversyck.slackintegration.web.controllers.restControllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

public abstract class AbstractRestController {
    @ExceptionHandler(ConstraintViolationException.class)
    private void handleConstraintViolationException(ConstraintViolationException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}
