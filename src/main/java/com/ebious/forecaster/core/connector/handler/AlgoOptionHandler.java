package com.ebious.forecaster.core.connector.handler;

import com.ebious.forecaster.core.domain.entity.starter.Starter;
import com.ebious.forecaster.core.domain.enums.Algorithm;
import com.ebious.forecaster.core.exception.RequestParserException;

import java.util.Arrays;
import java.util.List;


public class AlgoOptionHandler implements OptionHandler {
    private final static String OPTION_NAME = "-alg";
    private final static List<String> OPTION_VALUES = Algorithm.getStringValues();
    private final static String DEFAULT_OPTION_VALUE = Algorithm.DEFAULT.value;
    private static final String DESCRIPTION = "use -alg option to choose algorithm type\n" + "Excepted argument value: " + Arrays.toString(Algorithm.values());

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
        return name.equals(OPTION_NAME);
    }

    private boolean checkOptionValue(String value) {
        if (value.equals(DEFAULT_OPTION_VALUE)) return true;
        return OPTION_VALUES.contains(value);
    }

    private void setOptionValue(String value, Starter.Builder builder) {
        builder.algoType(Algorithm.getAlgorithm(value));
    }
}
