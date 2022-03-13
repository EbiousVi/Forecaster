package com.ebious.forecaster.core.utils;

import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final DateTimeFormatter PARSE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter PRINT_FORMATTER = DateTimeFormatter.ofPattern("EE dd.MM.yyyy");
}
