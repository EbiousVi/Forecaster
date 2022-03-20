package com.ebious.forecaster.model.cli.command.rate.parser;

import com.ebious.forecaster.model.cli.CommandLineParser;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.exception.OptionMapperException;
import com.ebious.forecaster.model.domain.entity.Starter;

import java.util.ArrayList;
import java.util.List;

import static com.ebious.forecaster.model.cli.domain.Info.SPLIT_BY_OPTION;

public class StarterParser implements CommandLineParser<Starter> {

    private static final String COMMAND_SEPARATOR = " ";
    private static final int CURRENCY_OPTION_INDEX = 0;
    private static final int DATE_OPTION_INDEX = 1;
    private static final int ALGO_OPTION_INDEX = 2;
    private static final int OUTPUT_OPTION_INDEX = 3;
    private static final int RATE_OPTION_SIZE = 4;

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

    private List<Option> splitByOptions(String commandLine) {
        String[] parts = split(commandLine);
        List<Option> options = collectOptions(parts);
        completeToFullOptionSize(options);
        return options;
    }

    private String[] split(String body) {
        String[] parts = body.split(COMMAND_SEPARATOR);
        if (parts.length % 2 != 0) throw new OptionMapperException(SPLIT_BY_OPTION);
        return parts;
    }

    private List<Option> collectOptions(String[] parts) {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < parts.length; i += 2) {
            options.add(new Option(parts[i], parts[i + 1]));
        }
        return options;
    }

    private void completeToFullOptionSize(List<Option> options) {
        if (options.size() <= RATE_OPTION_SIZE) {
            for (int i = options.size(); i < RATE_OPTION_SIZE; i++) {
                options.add(Option.getEmptyOption());
            }
        }
    }
}
