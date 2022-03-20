package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.exception.OptionMapperException;
import com.ebious.forecaster.model.utils.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateOptionMapper extends OptionMapper<ForecastDate> {

    private static final String DATE_OPTION_NAME = "-date";
    private static final String PERIOD_OPTION_NAME = "-period";
    private static final List<String> OPTION_NAMES = Arrays.asList(DATE_OPTION_NAME, PERIOD_OPTION_NAME);
    private static final String DESCRIPTION = "use -date or -period option to select date or period\n" +
            "Expected option name: -date" + System.lineSeparator() +
            "Excepted argument value: date at format like 22.03.2022" + System.lineSeparator() +
            "Expected option name: -period" + System.lineSeparator() +
            "Excepted argument value: " + Arrays.toString(Period.values()).toLowerCase();

    @Override
    protected ForecastDate mapToObject(Option option) {
        String value = option.getValue();
        if (isPeriodOption(option)) {
            return new ForecastDate(LocalDate.now(), Period.getPeriod(value));
        } else if (isDateOption(option)) {
            return new ForecastDate(LocalDate.parse(value, DateUtils.PARSE_FORMATTER), Period.DATE);
        }
        throw new OptionMapperException("Date is required option!");
    }

    @Override
    protected boolean isRequired(Option option) {
        return true;
    }

    @Override
    protected boolean validateOptionName(Option option) {
        return OPTION_NAMES.contains(option.getName());
    }

    @Override
    protected boolean validateOptionValue(Option option) {
        if (isPeriodOption(option)) return true;
        return isDateOption(option);
    }

    @Override
    protected void throwException(Option option) {
        String msg = String.format("Incorrect %s", option);
        throw new OptionMapperException(msg + System.lineSeparator() + DESCRIPTION);
    }

    private boolean isValidDate(String value) {
        try {
            LocalDate.parse(value, DateUtils.PARSE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isDateOption(Option option) {
        return option.getName().equals(DATE_OPTION_NAME) && isValidDate(option.getValue());
    }

    private boolean isPeriodOption(Option option) {
        return option.getName().equals(PERIOD_OPTION_NAME) && Period.contains(option.getValue());
    }
}