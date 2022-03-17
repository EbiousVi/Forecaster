package com.ebious.forecaster.model.utils;

import com.ebious.forecaster.model.domain.enums.Period;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final DateTimeFormatter PARSE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter PRINT_FORMATTER = DateTimeFormatter.ofPattern("EE dd.MM.yyyy");

    public static Long periodToEpochDay(Period period) {
        return LocalDate.now().toEpochDay() + period.daysCount;
    }
}
