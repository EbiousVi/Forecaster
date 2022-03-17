package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.cli.exception.OptionMapperException;
import com.ebious.forecaster.model.utils.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateOptionMapper extends OptionMapper<ForecastDate> {
    private final List<String> OPTION_NAMES = Arrays.asList("-date", "-period");
    private final String DESCRIPTION = "use -date or -period option to select forecast date\n" +
            "Excepted argument value to -period option: " + Arrays.toString(Period.values()).toLowerCase() + "\n" +
            "Excepted argument value to -date option: date like 22.03.2022 format";

    @Override
    protected ForecastDate mapToObject(Option option) {
        String value = option.getValue();
        if (isPeriodOption(option)) {
            return new ForecastDate(LocalDate.now(), Period.getPeriod(value));
        } else if (isDateOption(option)) {
            return new ForecastDate(LocalDate.parse(value, DateUtils.PARSE_FORMATTER), Period.NONE);
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
        if (isDateOption(option)) return true;
        return false;
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
        return option.getName().equals("-date") && isValidDate(option.getValue());
    }

    private boolean isPeriodOption(Option option) {
        return option.getName().equals("-period") && Period.contains(option.getValue());
    }
}