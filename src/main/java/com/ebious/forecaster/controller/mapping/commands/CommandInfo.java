package com.ebious.forecaster.controller.mapping.commands;

import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Period;

import java.util.Arrays;
import java.util.List;

public class CommandInfo {
    public final static String rateTemplate = ("rate(lower case)" +
            " + whitespace + " +
            "Currency(upper case),  values = " + Arrays.toString(Currency.values()) +
            " + whitespace + " +
            "interval(lower case) values = " + Arrays.toString(Period.values()));

    public final static String helpTemplate = "help";
    public final static String exitTemplate = "exit";

    public final static List<String> commandTemplates = Arrays.asList(rateTemplate, helpTemplate, exitTemplate);
    private static final StringBuilder stringBuilder = new StringBuilder();

    public static String getCommandTemplateString() {
        if (stringBuilder.length() != 0) return stringBuilder.toString();
        for (String commandTemplate : commandTemplates) {
            stringBuilder.append("-> ").append(commandTemplate).append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n"));
        return stringBuilder.toString();
    }
}
