package com.ebious.forecaster.model.forecaster;

import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.exception.ForecasterException;
import org.shredzone.commons.suncalc.MoonPhase;

import java.time.LocalDate;
import java.util.*;

public class ForecasterMoon implements Forecaster {

    private static final double PERCENT_OF_RANDOM_BOUND = 0.1;

    @Override
    public Map<Currency, List<Rate>> forecast(Map<Currency, List<Rate>> rates, ForecastDate forecastDate) {
        Map<Currency, List<Rate>> res = new EnumMap<>(Currency.class);
        for (Currency currency : rates.keySet()) {
            List<LocalDate> threePreviousFullMoonDate = findThreePreviousFullMoonDate(LocalDate.now());
            Double fullMoonRateValue = getFullMoonRateValue(rates.get(currency), threePreviousFullMoonDate);
            if (forecastDate.getPeriod().equals(Period.NONE)) {
                res.put(currency, Collections.singletonList(new Rate(forecastDate.getLocalDate(), fullMoonRateValue)));
            } else {
                List<Rate> ratesList = predictForPeriod(forecastDate, fullMoonRateValue);
                res.put(currency, ratesList);
            }
        }
        return res;
    }

    private List<Rate> predictForPeriod(ForecastDate forecastDate, Double fullMoonRateValue) {
        List<Rate> ratesList = new ArrayList<>();
        //Set fullMoonRateValue as pivot element to next prediction
        ratesList.add(new Rate(LocalDate.now().plusDays(1), fullMoonRateValue));
        Double prevPredictionValue = fullMoonRateValue;
        for (int startDay = 2; startDay <= forecastDate.getPeriod().daysCount; startDay++) {
            Double prediction = randomNextDayValue(prevPredictionValue);
            prevPredictionValue = prediction;
            ratesList.add(new Rate(LocalDate.now().plusDays(startDay), prediction));
        }
        return ratesList;
    }

    public Double getFullMoonRateValue(List<Rate> rates, List<LocalDate> threePrevFullMoonDate) {
        return rates.stream()
                .filter(rate -> {
                    for (LocalDate fullMoonDate : threePrevFullMoonDate) {
                        return rate.getDate().equals(fullMoonDate);
                    }
                    return false;
                })
                .mapToDouble(Rate::getCurs)
                .average()
                //Как бросать такие исключения? Что в них писать?
                .orElseThrow(() -> new ForecasterException("average"));
    }

    public Double randomNextDayValue(Double prevValue) {
        Double min = getMin(prevValue);
        Double max = getMax(prevValue);
        return new Random().nextInt((int) (max - min + 1)) + min;
    }

    public Double getMin(Double rateValue) {
        return rateValue - rateValue * PERCENT_OF_RANDOM_BOUND;
    }

    public Double getMax(Double rateValue) {
        return rateValue + rateValue * PERCENT_OF_RANDOM_BOUND;
    }

    private List<LocalDate> findThreePreviousFullMoonDate(LocalDate forecastDate) {
        List<LocalDate> res = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 0; res.size() < 3; i++) {
            LocalDate prevFullMoonDate = getLastFullMoonDate(forecastDate.minusDays(i));
            if (prevFullMoonDate.isBefore(now)) {
                res.add(prevFullMoonDate);
                forecastDate = prevFullMoonDate;
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
