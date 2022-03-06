package com.ebious.forecaster.forecast.forecast;

import com.ebious.forecaster.forecast.domain.Starter;
import com.ebious.forecaster.forecast.domain.forecast.Forecast;

import java.util.List;


public interface Forecaster<E> {

    List<Forecast> doForecast(List<E> dataSets, Starter starter);

    List<Double> getExchangeRateValues(List<E> dataSets);
}
