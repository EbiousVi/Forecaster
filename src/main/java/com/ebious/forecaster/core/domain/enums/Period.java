package com.ebious.forecaster.core.domain.enums;

import com.ebious.forecaster.core.exception.UnsupportedPeriodException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Period {
    WEEK(7),
    MONTH(30);

    public final Integer daysCount;

    Period(Integer daysCount) {
        this.daysCount = daysCount;
    }

    public static Period getPeriodByValue(String value) {
        for (Period period : values()) {
            if (period.name().toLowerCase().equals(value)) return period;
        }
        throw new UnsupportedPeriodException(
                "Unsupported period for Forecast! Available values = " + Arrays.toString(values()));
    }

    public static boolean contains(String value) {
        for (Period period : values()) {
            if (period.name().equalsIgnoreCase(value)) return true;
        }
        return false;
    }

    public static List<String> getStringValues() {
        List<String> values = new ArrayList<>();
        for (Period value : Period.values()) {
            values.add(value.name().toLowerCase());
        }
        return values;
    }
}
