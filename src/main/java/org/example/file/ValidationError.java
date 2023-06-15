package org.example.file;

import lombok.Getter;

public class ValidationError {

    private static ValidationError validationError = new ValidationError();

    @Getter
    private StringBuilder errors;

    private ValidationError() {
    }

    public static ValidationError getValidationError() {
        if (validationError == null) {
            validationError = new ValidationError();
        }
        return validationError;
    }

    public StringBuilder setError(String message) {
        return errors.append(message).append(System.lineSeparator());
    }

    public void clearMessages() {
        errors = new StringBuilder();
    }
}
