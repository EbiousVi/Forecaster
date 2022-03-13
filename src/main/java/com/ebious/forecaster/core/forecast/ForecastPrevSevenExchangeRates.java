package com.ebious.forecaster.core.forecast;


import com.ebious.forecaster.core.domain.entity.starter.Starter;
import com.ebious.forecaster.core.domain.entity.dataset.DataSet;
import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.core.domain.entity.forecast.Forecast;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Forecast based on seven previous exchange rates
 */
public class ForecastPrevSevenExchangeRates implements Forecaster {

    private static final int countOfLastPreviousExchangeRate = 7;

    @Override
    public Map<Currency, List<Forecast>> doForecast(Map<Currency, List<? extends DataSet>> dataSets, Starter starter) {
        Map<Currency, List<Forecast>> forecasts = new EnumMap<>(Currency.class);
        Set<Currency> currencies = dataSets.keySet();
        for (Currency currency : currencies) {
            List<Forecast> forecastList = new ArrayList<>();
            ArrayDeque<Double> queue = dataSets.get(currency).stream().map(DataSet::getCurs).collect(Collectors.toCollection(ArrayDeque::new));
            for (int i = 0, dateCounter = 1; i < starter.getEpochDay(); i++) {
                Double rateValue = averageByLastSevenRates(queue);
                forecastList.add(new Forecast(rateValue, LocalDate.now().plusDays(dateCounter++), starter.getCurrencies().get(0)));
                queue.pop();
                queue.add(rateValue);
            }
            forecasts.put(currency, forecastList);
        }
        return forecasts;
    }

    Double averageByLastSevenRates(Collection<Double> exchangeRateValues) {
        return exchangeRateValues.stream()
                .mapToDouble(d -> d)
                .average()
                .orElseThrow(() -> new RuntimeException("Internal Application Error!"));
    }
}
