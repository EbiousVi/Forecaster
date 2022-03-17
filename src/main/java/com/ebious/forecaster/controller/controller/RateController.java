package com.ebious.forecaster.controller.controller;

import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.model.cli.CommandLineParser;
import com.ebious.forecaster.model.cli.command.rate.parser.StarterParser;
import com.ebious.forecaster.model.domain.entity.Starter;
import com.ebious.forecaster.model.engine.ForecastEngine;
import com.ebious.forecaster.model.engine.ForecastEngineImpl;

public class RateController implements Controller {

    private final ForecastEngine forecastEngine = new ForecastEngineImpl();
    private final CommandLineParser<Starter> starterParser = new StarterParser();

    @Override
    public Response perform(Request request) {
        return forecastEngine.execute(starterParser.parse(request.getBody()));
    }
}
