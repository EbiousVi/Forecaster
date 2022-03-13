package com.ebious.forecaster.core.connector.handler;

import com.ebious.forecaster.core.domain.entity.starter.Starter;
import com.ebious.forecaster.core.domain.enums.Period;
import com.ebious.forecaster.core.exception.RequestParserException;
import com.ebious.forecaster.core.utils.DateUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DateOptionHandler implements OptionHandler {

    private static final String DESCRIPTION = "use -date/-period option to select forecast date\n" +
            "Excepted argument value to -period option: " + Arrays.toString(Period.values()).toLowerCase() + "\n" +
            "Excepted argument value to -date option: tomorrow alias or date like 22.03.2022 format" + "\n" +
            "Attention: -date arg value can be calculated only three months in ahead";
    private final static List<String> OPTION_NAMES = Arrays.asList("-date", "-period");
    private final static List<String> OPTION_VALUES = Period.getStringValues();
    private final static List<String> aliasValue = Collections.singletonList("tomorrow");

    @Override
    public void handle(String option, Starter.Builder builder) {
        boolean isValid = true;
        String[] token = option.split(" ");
        String name = token[OPTION_NAME_INDEX];
        String value = token[OPTION_VALUE_INDEX];
        if (!checkOptionName(name)) isValid = false;
        if (!checkOptionValue(value)) isValid = false;
        if (isValid) {
            setOptionValue(value, builder);
        } else {
            throw new RequestParserException("incorrect option name or arg <" + option + ">\n" + DESCRIPTION);
        }
    }

    private boolean checkOptionName(String name) {
        return OPTION_NAMES.contains(name);
    }

    private boolean checkOptionValue(String value) {
        if (Period.contains(value)) return true;
        if (aliasValue.contains(value)) return true;
        LocalDate parseDate = parseDate(value);
        LocalDate NOW = LocalDate.now();
        LocalDate LIMIT = LocalDate.now().plusMonths(3);
        if (parseDate.isBefore(NOW)) return false;
        if (parseDate.isAfter(NOW) && parseDate.isBefore(LIMIT)) return true;
        return false;
    }

    private void setOptionValue(String value, Starter.Builder builder) {
        LocalDate NOW = LocalDate.now().plusDays(1);
        if (aliasValue.contains(value)) {
            builder.epochDay(NOW.toEpochDay());
            return;
        }
        if (OPTION_VALUES.contains(value)) {
            builder.epochDay(NOW.plusDays(Period.getPeriodByValue(value).daysCount).toEpochDay());
            return;
        }
        builder.epochDay(parseDate(value).toEpochDay());
    }


    private LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value, DateUtils.PARSE_FORMATTER);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return LocalDate.MIN;
    }
}
