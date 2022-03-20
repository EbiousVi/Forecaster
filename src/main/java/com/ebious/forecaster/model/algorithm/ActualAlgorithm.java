package com.ebious.forecaster.model.algorithm;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.exception.AlgorithmException;
import com.ebious.forecaster.model.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculate sum of the exchange rate for (current year - 2 years) and (current year - 3 years)
 * If some value not found for these date, forecast is not possible
 */
public class ActualAlgorithm implements ForecastAlgorithm {

    @Override
    public Rate forecastOnADate(List<Rate> rates, LocalDate date) {
        Double forecast = sumOfLastYearsRates(rates, date);
        return new Rate(date, forecast);
    }

    @Override
    public List<Rate> forecastToPeriod(List<Rate> rates, Period period) {
        List<Rate> res = new ArrayList<>();
        for (int daysCounter = 1; daysCounter <= period.daysCount; daysCounter++) {
            LocalDate date = LocalDate.now().plusDays(daysCounter);
            Double forecast = sumOfLastYearsRates(rates, date);
            res.add(new Rate(date, forecast));
        }
        return res;
    }

    private Double sumOfLastYearsRates(List<Rate> rates, LocalDate date) {
        return getTwoYearsAgoRateValue(rates, date) + getThreeYearsAgoRateValue(rates, date);
    }

    private Double getTwoYearsAgoRateValue(List<Rate> rates, LocalDate date) {
        LocalDate twoYearsAgo = date.minusYears(2);
        return rates.stream()
                .filter(rate -> rate.getDate().equals(twoYearsAgo))
                .map(Rate::getCurs)
                .findAny()
                .orElseThrow(() -> new AlgorithmException("2 years ago rate not found at " +
                        twoYearsAgo.format(DateUtils.PRINT_FORMATTER)));
    }

    private Double getThreeYearsAgoRateValue(List<Rate> rates, LocalDate date) {
        LocalDate threeYearsAgo = date.minusYears(3);
        return rates.stream()
                .filter(rate -> rate.getDate().equals(threeYearsAgo))
                .map(Rate::getCurs)
                .findAny()
                .orElseThrow(() -> new AlgorithmException("3 years ago rate not found at " +
                        threeYearsAgo.format(DateUtils.PRINT_FORMATTER)));
    }
}
