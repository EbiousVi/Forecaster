package com.ebious.forecaster.core.connector.handler;

import com.ebious.forecaster.core.domain.entity.starter.Starter;
import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.core.exception.RequestParserException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CurrencyOptionHandler implements OptionHandler {

    private static final String OPTION_NAME = "rate";
    private static final List<Currency> OPTION_VALUES = Arrays.asList(Currency.values());
    private static final String DESCRIPTION = "rate uses to forecast exchange rate" +
            "Excepted argument value: " + Arrays.toString(Currency.values()) + "\n" +
            "or list of values separated by commas, like USD,EUR,TRY \n" +
            "The system supports only 5 exchange rates in the argument";

    @Override
    public void handle(String option, Starter.Builder builder) {
        boolean isValid = true;
        String[] token = option.split(" ");
        String name = token[OPTION_NAME_INDEX];
        String value = token[OPTION_VALUE_INDEX];
        if (!checkOptionName(name)) isValid = false;
        if (!checkOptionValue(value)) isValid = false;
        if (isValid) {
            setValue(value, builder);
        } else {
            throw new RequestParserException("incorrect option name or arg <" + option + ">\n" + DESCRIPTION);
        }
    }

    private boolean checkOptionName(String name) {
        return name.equals(OPTION_NAME);
    }

    private boolean checkOptionValue(String value) {
        if (value.length() == 3) {
            return OPTION_VALUES.contains(Currency.valueOf(value));
        }
        boolean isValid = true;
        if (value.length() > 3 && value.length() < 20) {
            String[] splittedArgs = value.split(",");
            for (String arg : splittedArgs) {
                if (!OPTION_VALUES.contains(Currency.valueOf(arg))) isValid = false;
            }
        }
        return isValid;
    }

    private void setValue(String value, Starter.Builder builder) {
        if (value.length() == 3) {
            builder.currency(Collections.singletonList(Currency.getCurrency(value)));
            return;
        }
        if (value.length() > 3 && value.length() < 20) {
            String[] splittedArgs = value.split(",");
            List<Currency> currencies = new ArrayList<>();
            for (String arg : splittedArgs) {
                currencies.add(Currency.getCurrency(arg));
            }
            builder.currency(currencies);
        }
    }
}
