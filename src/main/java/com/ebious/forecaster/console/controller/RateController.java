package com.ebious.forecaster.console.controller;

import com.ebious.forecaster.forecast.domain.Starter;
import com.ebious.forecaster.forecast.domain.enums.Currency;
import com.ebious.forecaster.forecast.engine.EngineImpl;
import com.ebious.forecaster.forecast.exception.UnsupportedCurrencyException;
import com.ebious.forecaster.forecast.exception.UnsupportedIntervalException;
import com.ebious.forecaster.forecast.domain.enums.Interval;
import com.ebious.forecaster.forecast.domain.enums.ConvertorType;

public class RateController implements Controller {

    private final EngineImpl engineImpl = EngineImpl.getEngine();

    @Override
    public String perform(String request) throws UnsupportedCurrencyException, UnsupportedIntervalException {
        Starter starter = parseArgs(request);
        return (String) engineImpl.start(starter);
    }

    private Starter parseArgs(String request) throws UnsupportedCurrencyException, UnsupportedIntervalException {
        String[] split = request.split(" ");
        return new Starter(
                Currency.getCurrency(split[1]),
                Interval.getInterval(split[2]),
                ConvertorType.CONSOLE);
    }
}
