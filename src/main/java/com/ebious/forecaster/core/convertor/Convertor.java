package com.ebious.forecaster.core.convertor;

import com.ebious.forecaster.core.domain.entity.forecast.Forecast;
import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.controller.gateway.Response;

import java.util.List;
import java.util.Map;

public interface Convertor {
    Response wrapToResponse(Map<Currency, List<Forecast>> currencyListMap);
}
