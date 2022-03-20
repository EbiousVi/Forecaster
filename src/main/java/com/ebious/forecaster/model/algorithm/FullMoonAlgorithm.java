package com.ebious.forecaster.model.algorithm;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.exception.AlgorithmException;
import org.shredzone.commons.suncalc.MoonPhase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Calculate average exchange rate of the last three full moon date.
 * Return the result for a specific date.
 * For the period, use the result as a pivot element. Subsequent dates are calculated recursively,
 * the value of the previous date plus a random number from -10% to +10% of the value of the previous date.
 */
public class FullMoonAlgorithm implements ForecastAlgorithm {

    private static final double PERCENT_OF_RANDOM_BOUND = 0.1;
    private static final double MISSING_FULL_MOON_RATE_VALUE = 90D;
    private static final int NUMBER_OF_FULL_MOON_DATES = 3;

    @Override
    public Rate forecastOnADate(List<Rate> rates, LocalDate date) {
        return new Rate(date, getFullMoonRateValue(rates, date));
    }

    @Override
    public List<Rate> forecastToPeriod(List<Rate> rates, Period period) {
        LocalDate now = LocalDate.now();
        List<Rate> res = new ArrayList<>();
        Double fullMoonRateValue = getFullMoonRateValue(rates, now);
        //Set fullMoonRateValue as pivot element to next prediction
        res.add(new Rate(now.plusDays(1), fullMoonRateValue));
        Double prevForecast = fullMoonRateValue;
        for (int daysCounter = 2; daysCounter <= period.daysCount; daysCounter++) {
            LocalDate date = now.plusDays(daysCounter);
            Double forecast = randomNextDayValue(prevForecast);
            prevForecast = forecast;
            res.add(new Rate(date, forecast));
        }
        return res;
    }

    /**
     * If there is no data for only one exchange rate, we use a replacement value.
     * If more than one is missing date, the forecast is not possible
     */
    private Double getFullMoonRateValue(List<Rate> rates, LocalDate date) {
        List<LocalDate> threePreviousFullMoonDate = findThreePreviousFullMoonDate(date);
        Double sumOfFullMoonDateRateValue = 0D;
        for (Rate rate : rates) {
            for (int i = 0; i < threePreviousFullMoonDate.size(); i++) {
                LocalDate fullMoonDate = threePreviousFullMoonDate.get(i);
                if (rate.getDate().equals(fullMoonDate)) {
                    sumOfFullMoonDateRateValue += rate.getCurs();
                    threePreviousFullMoonDate.remove(fullMoonDate);
                    if (threePreviousFullMoonDate.isEmpty()) break;
                }
            }
        }
        if (threePreviousFullMoonDate.size() == 1) {
            sumOfFullMoonDateRateValue += randomNextDayValue(MISSING_FULL_MOON_RATE_VALUE);
        }
        if (threePreviousFullMoonDate.size() > 1) {
            throw new AlgorithmException("Can't calculate Full moon rate value! " +
                    "Rates not found at = " + threePreviousFullMoonDate);
        }
        return sumOfFullMoonDateRateValue / NUMBER_OF_FULL_MOON_DATES;
    }

    private Double randomNextDayValue(Double prevValue) {
        Double min = getMin(prevValue);
        Double max = getMax(prevValue);
        return new Random().nextInt((int) (max - min + 1)) + min;
    }

    private Double getMin(Double rateValue) {
        return rateValue - rateValue * PERCENT_OF_RANDOM_BOUND;
    }

    private Double getMax(Double rateValue) {
        return rateValue + rateValue * PERCENT_OF_RANDOM_BOUND;
    }

    private List<LocalDate> findThreePreviousFullMoonDate(LocalDate date) {
        List<LocalDate> res = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 0; res.size() < NUMBER_OF_FULL_MOON_DATES; i++) {
            LocalDate prevFullMoonDate = getLastFullMoonDate(date.minusDays(i));
            if (prevFullMoonDate.isBefore(now)) {
                res.add(prevFullMoonDate);
                date = prevFullMoonDate;
                now = prevFullMoonDate;
            }
        }
        return res;
    }

    private LocalDate getLastFullMoonDate(LocalDate date) {
        return MoonPhase.compute()
                .on(date)
                .phase(MoonPhase.Phase.FULL_MOON)
                .execute()
                .getTime()
                .toLocalDate();
    }
}
