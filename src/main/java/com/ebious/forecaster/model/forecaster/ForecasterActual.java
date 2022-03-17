package com.ebious.forecaster.model.forecaster;

import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.exception.ForecasterException;

import java.time.LocalDate;
import java.util.*;

public class ForecasterActual implements Forecaster {

    @Override
    public Map<Currency, List<Rate>> forecast(Map<Currency, List<Rate>> rates, ForecastDate date) {
        Map<Currency, List<Rate>> res = new EnumMap<>(Currency.class);
        for (Currency currency : rates.keySet()) {
            if (date.getPeriod().equals(Period.NONE)) {
                Rate rate = predictForDate(rates.get(currency), date);
                res.put(currency, Collections.singletonList(rate));
            } else {
                List<Rate> rateList = predictForPeriod(rates.get(currency), date);
                res.put(currency, rateList);
            }
        }
        return res;
    }

    private List<Rate> predictForPeriod(List<Rate> rates, ForecastDate date) {
        List<Rate> res = new ArrayList<>();
        for (int startDay = 1; startDay < date.getPeriod().daysCount; startDay++) {
            Double prediction = sumOfLastYearsCursValue(rates, date.getLocalDate().plusDays(startDay));
            res.add(new Rate(LocalDate.now().plusDays(startDay), prediction));
        }
        return res;
    }

    private Rate predictForDate(List<Rate> rates, ForecastDate date) {
        Double prediction = sumOfLastYearsCursValue(rates, date.getLocalDate());
        return new Rate(date.getLocalDate(), prediction);
    }

    private Double sumOfLastYearsCursValue(List<Rate> rates, LocalDate date) {
        return getTwoYearsAgoRateValue(rates, date) + getThreeYearsAgoRateValue(rates, date);
    }

    private Double getTwoYearsAgoRateValue(List<Rate> rates, LocalDate date) {
        LocalDate twoYearsAgo = date.minusYears(2);
        return rates.stream()
                .filter(rate -> rate.getDate().equals(twoYearsAgo))
                .map(Rate::getCurs)
                .findAny()
                .orElseThrow(() -> new ForecasterException("2 years ago rate not found at " + twoYearsAgo));
    }

    private Double getThreeYearsAgoRateValue(List<Rate> rates, LocalDate date) {
        LocalDate threeYearsAgo = date.minusYears(3);
        return rates.stream()
                .filter(rate -> rate.getDate().equals(threeYearsAgo))
                .map(Rate::getCurs)
                .findAny()
                .orElseThrow(() -> new ForecasterException("3 years ago rate not found at " + threeYearsAgo));
    }
}
