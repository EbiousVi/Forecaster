package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.cli.exception.OptionMapperException;

import java.util.Arrays;

public class OutputOptionMapper extends OptionMapper<Output> {

    private final static String OPTION_NAME = "-output";
    private static final String DESCRIPTION = "use " + OPTION_NAME + " option to set output\n" +
            "Option is not required, by default return text message\n" +
            "Expected option value: " + OPTION_NAME + "\n" +
            "Excepted argument value: " + Arrays.toString(Output.values()).toLowerCase();

    @Override
    protected Output mapToObject(Option option) {
        if (Option.getEmptyOption().equals(option)) return Output.DEFAULT;
        return Output.getOutput(option.getValue());
    }

    @Override
    protected boolean isRequired(Option option) {
        return !option.equals(Option.getEmptyOption());
    }

    @Override
    protected boolean validateOptionName(Option option) {
        return option.getName().equals(OPTION_NAME);
    }

    @Override
    protected boolean validateOptionValue(Option option) {
        return Output.contains(option.getValue());
    }

    @Override
    protected void throwException(Option option) {
        String msg = String.format("Incorrect %s", option);
        throw new OptionMapperException(msg + System.lineSeparator() + DESCRIPTION);
    }
}
