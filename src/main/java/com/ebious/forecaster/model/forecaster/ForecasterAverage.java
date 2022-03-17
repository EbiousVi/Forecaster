package com.ebious.forecaster.model.forecaster;

import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.exception.InternalAppError;
import com.ebious.forecaster.model.utils.DateUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.ebious.forecaster.model.utils.ForecasterUtils.getLastRateAsList;

/**
 * Forecast based on seven previous exchange rates
 */
public class ForecasterAverage implements Forecaster {
    /**
     * Number of recent required rates to predict!
     */
    private final static int RECENT_REQUIRED_RATES_TO_PREDICT = 7;

    @Override
    public Map<Currency, List<Rate>> forecast(Map<Currency, List<Rate>> rates, ForecastDate date) {
        Map<Currency, List<Rate>> res = new EnumMap<>(Currency.class);
        for (Currency currency : rates.keySet()) {
            if (date.getPeriod().equals(Period.NONE)) {
                List<Rate> predictionRates = predict(rates.get(currency), date.getLocalDate().toEpochDay());
                res.put(currency, getLastRateAsList(predictionRates));
            } else {
                res.put(currency, predict(rates.get(currency), DateUtils.periodToEpochDay(date.getPeriod())));
            }
        }
        return res;
    }

    public List<Rate> predict(List<Rate> rates, Long forecastEpochDay) {
        List<Rate> res = new ArrayList<>();
        LocalDate now = LocalDate.now();
        long nowEpochDay = now.toEpochDay();
        ArrayDeque<Double> queue = getRequiredRatesToPredict(rates);
        for (long startDay = 1; nowEpochDay < forecastEpochDay; startDay++, nowEpochDay++) {
            Double avgRateValue = averageByLastSevenRates(queue);
            res.add(new Rate(now.plusDays(startDay), avgRateValue));
            queue.pop();
            queue.add(avgRateValue);
        }
        return res;
    }

    private ArrayDeque<Double> getRequiredRatesToPredict(List<Rate> rates) {
        return rates.stream()
                .limit(RECENT_REQUIRED_RATES_TO_PREDICT)
                .map(Rate::getCurs)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    private Double averageByLastSevenRates(Collection<Double> rateValues) {
        return rateValues.stream()
                .mapToDouble(d -> d)
                .average()
                //What kind exception need to throw here?!
                .orElseThrow(() -> new InternalAppError("What kind exception need to throw here?!"));
    }
}