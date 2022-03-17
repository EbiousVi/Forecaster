package com.ebious.forecaster.model.exception;

public class DataSetSupplierException  extends RuntimeException{
    public DataSetSupplierException(String message) {
        super(message);
    }

    public DataSetSupplierException(String message, Throwable cause) {
        super(message, cause);
    }
}
