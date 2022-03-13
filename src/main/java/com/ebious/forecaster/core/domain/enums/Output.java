package com.ebious.forecaster.core.domain.enums;

import com.ebious.forecaster.core.exception.UnsupportedOutputTypeException;

import java.util.Arrays;

public enum Output {
    DEFAULT("default"),
    LIST("list"),
    GRAPH("graph");

    public final String value;

    Output(String value) {
        this.value = value;
    }

    public static Output getOutput(String value) {
        for (Output output : values()) {
            if (output.value.equals(value)) return output;
        }
        throw new UnsupportedOutputTypeException(
                "Unsupported output type for Forecast! Available values = " + Arrays.toString(values()));
    }

    public static boolean contains(String value) {
        for (Output output : values()) {
            if (output.value.equals(value)) return true;
        }
        return false;
    }
}
