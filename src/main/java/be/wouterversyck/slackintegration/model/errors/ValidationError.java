package be.wouterversyck.slackintegration.model.errors;

import lombok.Value;

@Value
public class ValidationError {
    private String message;

    public ValidationError(String message) {
        this.message = message;
    }
}
