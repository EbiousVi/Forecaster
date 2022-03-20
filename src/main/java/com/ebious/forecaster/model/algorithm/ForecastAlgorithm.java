package com.ebious.forecaster.model.algorithm;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Period;

import java.time.LocalDate;
import java.util.List;

public interface ForecastAlgorithm {

    Rate forecastOnADate(List<Rate> rates, LocalDate date);

    List<Rate> forecastToPeriod(List<Rate> rates, Period period);
}
