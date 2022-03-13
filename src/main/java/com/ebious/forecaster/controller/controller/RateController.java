package com.ebious.forecaster.controller.controller;

import com.ebious.forecaster.core.connector.ForecastEngineConnector;
import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.controller.gateway.Response;

public class RateController implements Controller {

    private final ForecastEngineConnector forecastEngineConnector = new ForecastEngineConnector();

    @Override
    public Response perform(Request request) {
        return forecastEngineConnector.connect(request);
    }
}
