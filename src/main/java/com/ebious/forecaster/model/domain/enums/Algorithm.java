package com.ebious.forecaster.model.domain.enums;

import com.ebious.forecaster.model.exception.UnsupportedAlgorithmException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Algorithm {
    LINEAR("linear"),
    ACTUAL("actual"),
    MOON("moon"),
    AVG("avg");

    public final String value;

    Algorithm(String value) {
        this.value = value;
    }

    public static Algorithm getAlgorithm(String value) {
        for (Algorithm algo : values()) {
            if (algo.value.equals(value)) return algo;
        }
        throw new UnsupportedAlgorithmException("Unsupported algorithm " + value + " for Forecast! Available values = " + Arrays.toString(values()).toLowerCase());
    }

    public static boolean contains(String value) {
        for (Algorithm algo : values()) {
            if (algo.value.equals(value)) return true;
        }
        return false;
    }
}
