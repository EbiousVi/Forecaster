package com.ebious.forecaster.forecast.convertor;

import com.ebious.forecaster.forecast.domain.forecast.Forecast;

import java.util.List;

public interface Convertor<T> {
    T wrap(List<Forecast> forecasts);
}
