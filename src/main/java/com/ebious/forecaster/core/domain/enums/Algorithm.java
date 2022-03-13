package com.ebious.forecaster.core.domain.enums;

import com.ebious.forecaster.core.exception.UnsupportedAlgorithmException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Algorithm {
    DEFAULT("linear"),
    LINEAR("linear");

    public final String value;

    Algorithm(String value) {
        this.value = value;
    }

    public static Algorithm getAlgorithm(String value) {
        for (Algorithm algo : values()) {
            if (algo.value.equals(value)) return algo;
        }
        throw new UnsupportedAlgorithmException("Unsupported algorithm type = " + " for Forecast! Available values = " + Arrays.toString(values()));
    }

    public static boolean contains(String value) {
        for (Algorithm algo : values()) {
            if (algo.value.equals(value)) return true;
        }
        return false;
    }

    public static List<String> getStringValues() {
        List<String> values = new ArrayList<>();
        for (Algorithm value : Algorithm.values()) {
            values.add(value.name());
        }
        return values;
    }
}
