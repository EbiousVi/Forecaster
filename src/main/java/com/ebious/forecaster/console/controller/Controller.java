package com.ebious.forecaster.console.controller;

import com.ebious.forecaster.forecast.exception.UnsupportedCurrencyException;
import com.ebious.forecaster.forecast.exception.UnsupportedIntervalException;

public interface Controller {

    String perform(String request) throws UnsupportedCurrencyException, UnsupportedIntervalException;
}
