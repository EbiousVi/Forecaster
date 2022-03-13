package com.ebious.forecaster.core.engine;

import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.core.domain.entity.starter.Starter;

public interface Engine {
    Response start(Starter starter);
}
