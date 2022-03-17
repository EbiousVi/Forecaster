package com.ebious.forecaster.model.forecaster;

import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;

import java.util.List;
import java.util.Map;

public interface Forecaster {
    Map<Currency, List<Rate>> forecast(Map<Currency, List<Rate>> rates, ForecastDate date);
}
