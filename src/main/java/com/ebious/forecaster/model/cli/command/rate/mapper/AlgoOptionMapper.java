package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.domain.enums.Algorithm;
import com.ebious.forecaster.model.cli.exception.OptionMapperException;

import java.util.Arrays;

public class AlgoOptionMapper extends OptionMapper<Algorithm> {

    private final static String OPTION_NAME = "-alg";
    private static final String DESCRIPTION = "use -alg option to choose forecast algorithm type\n" +
            "Expected option value: " + OPTION_NAME + "\n" +
            "Expected argument value: " + Arrays.toString(Algorithm.values()).toLowerCase();

    @Override
    protected Algorithm mapToObject(Option option) {
        return Algorithm.getAlgorithm(option.getValue());
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
        return Algorithm.contains(option.getValue());
    }

    @Override
    protected void throwException(Option option) {
        String msg = String.format("Incorrect %s", option);
        throw new OptionMapperException(msg + System.lineSeparator() + DESCRIPTION);
    }
}
