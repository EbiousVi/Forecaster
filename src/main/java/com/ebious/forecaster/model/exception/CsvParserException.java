package com.ebious.forecaster.model.exception;

public class CsvParserException extends RuntimeException {
    public CsvParserException(String message) {
        super(message);
    }

    public CsvParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
