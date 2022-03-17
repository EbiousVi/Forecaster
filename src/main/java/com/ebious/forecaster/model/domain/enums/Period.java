package com.ebious.forecaster.model.domain.enums;

import com.ebious.forecaster.model.exception.UnsupportedPeriodException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Period {
    NONE(0L),
    TOMORROW(1L),
    WEEK(7L),
    MONTH(30L);

    public final Long daysCount;

    Period(Long daysCount) {
        this.daysCount = daysCount;
    }

    public static Period getPeriod(String value) {
        for (Period period : values()) {
            if (period.name().toLowerCase().equals(value)) return period;
        }
        throw new UnsupportedPeriodException(
                "Unsupported period for Forecast! Available values = " + Arrays.toString(values()));
    }

    public static boolean contains(String value) {
        for (Period period : values()) {
            if (period.name().toLowerCase().equals(value)) return true;
        }
        return false;
    }
}
