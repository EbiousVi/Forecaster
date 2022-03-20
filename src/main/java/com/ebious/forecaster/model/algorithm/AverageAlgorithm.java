package com.ebious.forecaster.model.algorithm;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.exception.AlgorithmException;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Forecast based on average seven previous exchange rates
 */
public class AverageAlgorithm implements ForecastAlgorithm {

    /**
     * Number of recent required rates to forecast!
     */
    private final static int RECENT_REQUIRED_RATES_TO_FORECAST = 7;

    @Override
    public Rate forecastOnADate(List<Rate> rates, LocalDate forecastDate) {
        Double avgRateValue = 0D;
        long start = LocalDate.now().toEpochDay();
        long end = forecastDate.toEpochDay();
        ArrayDeque<Double> queue = getRequiredRatesToForecast(rates);
        for (; start < end; start++) {
            avgRateValue = averageByLastSevenRates(queue);
            queue.pop();
            queue.add(avgRateValue);
        }
        return new Rate(forecastDate, avgRateValue);
    }

    @Override
    public List<Rate> forecastToPeriod(List<Rate> rates, Period period) {
        List<Rate> res = new ArrayList<>();
        ArrayDeque<Double> queue = getRequiredRatesToForecast(rates);
        for (long daysCounter = 1; daysCounter <= period.daysCount; daysCounter++) {
            Double avgRateValue = averageByLastSevenRates(queue);
            LocalDate date = LocalDate.now().plusDays(daysCounter);
            res.add(new Rate(date, avgRateValue));
            queue.pop();
            queue.add(avgRateValue);
        }
        return res;
    }

    private ArrayDeque<Double> getRequiredRatesToForecast(List<Rate> rates) {
        return rates.stream()
                .limit(RECENT_REQUIRED_RATES_TO_FORECAST)
                .map(Rate::getCurs)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    private Double averageByLastSevenRates(Collection<Double> rateValues) {
        return rateValues.stream()
                .mapToDouble(d -> d)
                .average()
                .orElseThrow(() -> new AlgorithmException("Can't calculate average by last 7 rates." +
                        " Incoming rateValues size = " + rateValues.size()));
    }
}
