package com.ebious.forecaster.model.domain.enums;

import com.ebious.forecaster.model.exception.UnsupportedOutputException;

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
        throw new UnsupportedOutputException(
                "Unsupported output type for Forecast! Available values = " + Arrays.toString(values()));
    }

    public static boolean contains(String value) {
        for (Output output : values()) {
            if (output.value.equals(value)) return true;
        }
        return false;
    }
}
