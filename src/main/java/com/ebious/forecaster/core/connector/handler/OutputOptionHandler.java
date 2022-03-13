package com.ebious.forecaster.core.connector.handler;

import com.ebious.forecaster.core.domain.entity.starter.Starter;
import com.ebious.forecaster.core.domain.enums.Output;
import com.ebious.forecaster.core.domain.enums.Period;
import com.ebious.forecaster.core.exception.RequestParserException;

import java.util.Arrays;

public class OutputOptionHandler implements OptionHandler {

    private final static String optionName = "-output";
    private final static String defaultValue = Output.DEFAULT.value;
    private static final String DESCRIPTION = "use -output option to set output type\n" +
            "Excepted argument value to -period option: " + Arrays.toString(Period.values()) + "\n" +
            "Excepted argument value to -date option: tomorrow alias or date like 22.03.2022";

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
        return name.equals(optionName);
    }

    private boolean checkOptionValue(String value) {
        if (value.equals(defaultValue)) return true;
        return Output.contains(value);
    }

    private void setOptionValue(String value, Starter.Builder builder) {
        builder.outputType(Output.getOutput(value));
    }
}
