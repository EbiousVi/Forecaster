package com.ebious.forecaster.controller.exception;

public class RequestMappingException extends RuntimeException {
    public RequestMappingException(String message) {
        super(message);
    }
}
