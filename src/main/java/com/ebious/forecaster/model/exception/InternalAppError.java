package com.ebious.forecaster.model.exception;

public class InternalAppError extends RuntimeException {
    public InternalAppError(String message) {
        super(message);
    }

    public InternalAppError(String message, Throwable cause) {
        super(message, cause);
    }
}
