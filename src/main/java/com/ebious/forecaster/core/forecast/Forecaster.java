package com.ebious.forecaster.core.forecast;

import com.ebious.forecaster.core.domain.entity.starter.Starter;
import com.ebious.forecaster.core.domain.entity.dataset.DataSet;
import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.core.domain.entity.forecast.Forecast;

import java.util.List;
import java.util.Map;

public interface Forecaster {

    Map<Currency, List<Forecast>> doForecast(Map<Currency, List<? extends DataSet>> dataSet, Starter starter);
}
