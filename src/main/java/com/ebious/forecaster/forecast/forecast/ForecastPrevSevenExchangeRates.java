package com.ebious.forecaster.forecast.forecast;

import com.ebious.forecaster.forecast.domain.Starter;
import com.ebious.forecaster.forecast.domain.dataset.CSVDataSetObject;
import com.ebious.forecaster.forecast.domain.forecast.Forecast;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Naming is hard...
 * ForecastBasedOnSevenPrevExchangeRates
 * Forecast based on seven previous exchange rates
 */
public class ForecastPrevSevenExchangeRates implements Forecaster<CSVDataSetObject> {

    private static final int countOfLastPreviousExchangeRate = 7;

    @Override
    public List<Forecast> doForecast(List<CSVDataSetObject> dataSets, Starter starter) {
        List<Forecast> forecasts = new ArrayList<>();
        ArrayDeque<Double> queue = new ArrayDeque<>(getExchangeRateValues(dataSets));
        for (int i = 0, dateCounter = 1; i < starter.getInterval().value; i++) {
            Double rateValue = averageByLastSevenRates(queue);
            forecasts.add(new Forecast(rateValue, LocalDate.now().plusDays(dateCounter++), starter.getCurrency()));
            queue.pop();
            queue.add(rateValue);
        }
        return forecasts;
    }

    @Override
    public List<Double> getExchangeRateValues(List<CSVDataSetObject> dataSets) {
        return dataSets.stream()
                .map(d -> Double.parseDouble(d.getCurs()))
                .limit(countOfLastPreviousExchangeRate)
                .collect(Collectors.toList());
    }

    Double averageByLastSevenRates(Collection<Double> exchangeRateValues) {
        return exchangeRateValues.stream()
                .mapToDouble(d -> d)
                .average()
                .orElseThrow(() -> new RuntimeException("Internal Application Error!"));
    }
}
