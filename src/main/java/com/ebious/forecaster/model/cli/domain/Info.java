package com.ebious.forecaster.model.cli.domain;

import com.ebious.forecaster.model.domain.enums.Algorithm;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.domain.enums.Period;

import java.util.Arrays;

public class Info {
    public static final String LIST_OF_COMMAND = "List of available command\n";
    public static final String INFO_COMMAND = "--> HELP COMMAND\n Just type - help or <help : command name>\n";
    public static final String RATE_COMMAND = "--> RATE COMMAND\n" +
            "Looks like: <-opt : arg>(0) <-opt : arg>(1) <-opt : arg>(2)\n" +
            "Words need separated by whitespace\n" +
            "index(0) <rate : value or list of values separated by comma>\n" +
            "  values: " + Arrays.toString(Currency.values()) + "\n" +
            "index(1) has some of options\n" +
            "  <-period : values " + Arrays.toString(Period.values()).toLowerCase() + ">\n" +
            "  <-date : some date at format dd.MM.yyyy>\n" +
            "index(2) <-alg : values " + Arrays.toString(Algorithm.values()).toLowerCase() + ">\n" +
            "index(3) <-output : values " + Arrays.toString(Output.values()).toLowerCase() + ">" +
            "\nExample\n" +
            "rate USD -date 30.03.2022 -alg linear\n" +
            "rate USD,EUR -period week -alg moon -output graph";
    public static final String SPLIT_BY_OPTION = "Incorrect command structure!\n" +
            "Command consists of blocks of 2 words, each block is an option, the option must have an argument\n" +
            "Options have a strict order, depending on the command\n" +
            "It's look like: <-opt : arg> <-opt : arg> <-opt : arg>\n" +
            "try <help : command name> for details";
}