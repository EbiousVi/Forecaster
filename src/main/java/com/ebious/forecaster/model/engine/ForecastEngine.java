package com.ebious.forecaster.model.engine;

import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.model.domain.entity.Starter;

public interface ForecastEngine {
    Response execute(Starter starter);
}
