package com.ebious.forecaster.forecast.domain.enums;

import com.ebious.forecaster.forecast.exception.UnsupportedIntervalException;

import java.util.Arrays;

public enum Interval {
    TOMORROW(1),
    WEEK(7);

    public final Integer value;

    Interval(Integer value) {
        this.value = value;
    }

    public static Interval getInterval(String value) throws UnsupportedIntervalException {
        for (Interval interval : values()) {
            if (interval.name().equalsIgnoreCase(value)) return interval;
        }
        throw new UnsupportedIntervalException(
                "Unsupported Interval for Forecast! Available values = " + Arrays.toString(values()));
    }
}
