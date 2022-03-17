package com.ebious.forecaster.model.convertor;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.controller.gateway.Response;

import java.util.List;
import java.util.Map;

public interface Convertor {
    Response convert(Map<Currency, List<Rate>> rates);
}
