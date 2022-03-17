package com.ebious.forecaster.model.cli.domain;

import com.ebious.forecaster.model.domain.enums.Algorithm;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.domain.enums.Period;

import java.util.Arrays;

public class Info {
    public static final String LIST_OF_COMMAND = "List of available command:\n";
    public static final String INFO_COMMAND = "--> HELP COMMAND\n Just type: help\n";
    public static final String RATE_COMMAND = "--> RATE COMMAND\n" +
            "Words need split by whitespace\n" +
            "index(0) rate value or list of values splitted by comma\n" +
            "  Available values: " + Arrays.toString(Currency.values()) + "\n" +
            "index(1) has some of options\n" +
            "  -period - available values: " + Arrays.toString(Period.values()).toLowerCase() + "\n" +
            "  -date - some date at format dd.MM.yyyy\n" +
            "index(2) -alg available - values: " + Arrays.toString(Algorithm.values()).toLowerCase() + "\n" +
            "index(3) -output - available values: " + Arrays.toString(Output.values()).toLowerCase() + "\nExample\n" +
            "rate USD -date 30.03.2022 -alg linear\n" +
            "rate USD,EUR -period week -alg moon -output graph";
    public static final String SPLIT_BY_OPTION = "Incorrect command structure!\n" +
            "Command consists of blocks of 2 words, each block is an option, the option must have an argument\n" +
            "Options have a strict order or index. It's look like:\n" +
            "<-opt:arg> (index 0), <-opt:arg> (index 1), <-opt:arg> (index 2)";
}
