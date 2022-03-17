package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.cli.exception.OptionMapperException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CurrencyOptionMapper extends OptionMapper<List<Currency>> {

    private static final String OPTION_NAME = "rate";
    private static final int SINGLE_CURRENCY_LENGTH = 3;
    private static final int LIMIT_CURRENCY_LENGTH = 20;
    private static final String CURRENCIES_SEPARATOR = ",";
    private final String DESCRIPTION = "rate uses to forecast exchange rate\n" +
            "Excepted argument value: " + Arrays.toString(Currency.values()) + "\n" +
            "or list of values separated by commas, like: USD,EUR,TRY\n" +
            "The system supports only 5 exchange rates in the argument";

    @Override
    protected List<Currency> mapToObject(Option option) {
        String value = option.getValue();
        if (isSingleCurrency(value)) {
            return Collections.singletonList(Currency.getCurrency(value));
        }
        List<Currency> currencies = new ArrayList<>();
        if (isMultiCurrency(value)) {
            String[] args = value.split(CURRENCIES_SEPARATOR);
            for (String arg : args) {
                currencies.add(Currency.getCurrency(arg));
            }
        }
        return currencies;
    }

    @Override
    protected boolean isRequired(Option option) {
        return true;
    }

    @Override
    protected boolean validateOptionName(Option option) {
        return option.getName().equals(OPTION_NAME);
    }

    @Override
    protected boolean validateOptionValue(Option option) {
        String value = option.getValue();
        if (isSingleCurrency(value)) {
            return Currency.contains(value);
        }
        if (isMultiCurrency(value)) {
            String[] splittedArgs = option.getValue().split(",");
            for (String arg : splittedArgs) {
                if (!Currency.contains(arg)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected void throwException(Option option) {
        String msg = String.format("Incorrect %s", option);
        throw new OptionMapperException(msg + System.lineSeparator() + DESCRIPTION);
    }

    private boolean isMultiCurrency(String value) {
        return value.length() > SINGLE_CURRENCY_LENGTH && value.length() < LIMIT_CURRENCY_LENGTH;
    }

    private boolean isSingleCurrency(String value) {
        return value.length() == SINGLE_CURRENCY_LENGTH;
    }
}
