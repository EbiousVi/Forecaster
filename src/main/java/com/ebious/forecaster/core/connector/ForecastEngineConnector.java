package com.ebious.forecaster.core.connector;

import com.ebious.forecaster.core.connector.handler.*;
import com.ebious.forecaster.core.domain.entity.starter.Starter;
import com.ebious.forecaster.core.engine.Engine;
import com.ebious.forecaster.core.engine.EngineImpl;
import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.core.exception.RequestParserException;

import java.util.*;

public class ForecastEngineConnector {

    private static final int CURRENCY_OPTION_INDEX = 0;
    private static final int PERIOD_OPTION_INDEX = 1;
    private static final int ALGO_OPTION_INDEX = 2;
    private static final int OUTPUT_OPTION_INDEX = 3;
    private static final String COMMAND_SEPARATOR = " ";
    private final Map<Integer, OptionHandler> commandChain = new HashMap<>();
    private final Engine engine = EngineImpl.getEngine();

    {
        commandChain.put(CURRENCY_OPTION_INDEX, new CurrencyOptionHandler());
        commandChain.put(PERIOD_OPTION_INDEX, new DateOptionHandler());
        commandChain.put(ALGO_OPTION_INDEX, new AlgoOptionHandler());
        commandChain.put(OUTPUT_OPTION_INDEX, new OutputOptionHandler());
    }

    public Response connect(Request request) {
        return engine.start(parseRequest(request));
    }

    private Starter parseRequest(Request request) {
        String body = request.getBody();
        List<String> options = splitRequestByOptions(body);
        Starter.Builder builder = new Starter.Builder();
        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            OptionHandler optionHandler = commandChain.get(i);
            optionHandler.handle(option, builder);
        }
        return builder.build();
    }

    private List<String> splitRequestByOptions(String body) {
        String[] words = body.split(COMMAND_SEPARATOR);
        if (words.length % 2 != 0) throw new RequestParserException("Incorrect word length! Request command consists of blocks of 2 words, each block is an option, the option must have an argument. Try help to write correct request command!");
        List<String> options = new ArrayList<>();
        for (int i = 0; i < words.length; i += 2) {
            options.add(words[i] + COMMAND_SEPARATOR + words[i + 1]);
        }
        return options;
    }
}
