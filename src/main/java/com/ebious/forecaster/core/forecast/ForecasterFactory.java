package com.ebious.forecaster.core.forecast;

import com.ebious.forecaster.core.domain.enums.Algorithm;

import java.util.EnumMap;
import java.util.Map;

public class ForecasterFactory {
    private final Map<Algorithm, Forecaster> forecastFactory = new EnumMap<>(Algorithm.class);

    {
        forecastFactory.put(Algorithm.LINEAR, new ForecastLinear());
        forecastFactory.put(Algorithm.DEFAULT, new ForecastLinear());
    }

    public Forecaster getForecaster(Algorithm type) {
        if (forecastFactory.containsKey(type)) {
            return forecastFactory.get(type);
        }
        throw new RuntimeException("Support only linear algorithm");
    }
}
