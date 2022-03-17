package com.ebious.forecaster.model.cli.command.rate.parser;

import com.ebious.forecaster.model.cli.CommandLineParser;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.cli.exception.OptionMapperException;
import com.ebious.forecaster.model.domain.entity.Starter;

import java.util.ArrayList;
import java.util.List;

import static com.ebious.forecaster.model.cli.domain.Info.SPLIT_BY_OPTION;


public class StarterParser implements CommandLineParser<Starter> {

    private static final int CURRENCY_OPTION_INDEX = 0;
    private static final int DATE_OPTION_INDEX = 1;
    private static final int ALGO_OPTION_INDEX = 2;
    private static final int OUTPUT_OPTION_INDEX = 3;
    private static final int FULL_OPTIONS_SIZE = 4;
    private static final String COMMAND_SEPARATOR = " ";

    @Override
    public Starter parse(String commandLine) {
        List<Option> options = splitByOptions(commandLine);
        return new Starter.OptionBuilder()
                .currencies(options.get(CURRENCY_OPTION_INDEX))
                .forecastDate(options.get(DATE_OPTION_INDEX))
                .algorithm(options.get(ALGO_OPTION_INDEX))
                .output(options.get(OUTPUT_OPTION_INDEX))
                .build();
    }

    private List<Option> splitByOptions(String body) {
        String[] words = body.split(COMMAND_SEPARATOR);
        checkCommandStructure(words);
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < words.length; i += 2) {
            options.add(new Option(words[i], words[i + 1]));
        }
        fillNonRequiredOptions(options);
        return options;
    }

    private void checkCommandStructure(String[] words) {
        if (words.length % 2 != 0) {
            throw new OptionMapperException(SPLIT_BY_OPTION);
        }
    }

    private void fillNonRequiredOptions(List<Option> options) {
        if (options.size() <= FULL_OPTIONS_SIZE) {
            for (int i = options.size() - 1; i < FULL_OPTIONS_SIZE; i++) {
                options.add(Option.getEmptyOption());
            }
        }
    }
}
