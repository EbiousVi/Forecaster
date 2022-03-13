package com.ebious.forecaster.core.connector.handler;

import com.ebious.forecaster.core.domain.entity.starter.Starter;

public interface OptionHandler {
    int OPTION_NAME_INDEX = 0;
    int OPTION_VALUE_INDEX = 1;

    void handle(String token, Starter.Builder builder);
}
