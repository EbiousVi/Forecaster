package com.ebious.forecaster.forecast.engine;

import com.ebious.forecaster.forecast.domain.Starter;

public interface Engine {
    Object start(Starter starter);
}
