package com.ebious.forecaster.model.forecaster;

import com.ebious.forecaster.model.algorithm.*;
import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Algorithm;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.exception.UnsupportedAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ForecasterImpl implements Forecaster {

    private static final Logger logger = LoggerFactory.getLogger(ForecasterImpl.class);
    private final Map<Algorithm, ForecastAlgorithm> factory = new EnumMap<>(Algorithm.class);

    {
        factory.put(Algorithm.LINEAR, new LinearRegressionAlgorithm());
        factory.put(Algorithm.MOON, new FullMoonAlgorithm());
        factory.put(Algorithm.ACTUAL, new ActualAlgorithm());
        factory.put(Algorithm.AVG, new AverageAlgorithm());
    }

    @Override
    public Map<Currency, List<Rate>> forecast(Map<Currency, List<Rate>> dateSet, ForecastDate date, Algorithm algorithm) {
        Map<Currency, List<Rate>> res = new EnumMap<>(Currency.class);
        for (Currency currency : dateSet.keySet()) {
            if (date.getPeriod().equals(Period.DATE)) {
                Rate rate = getAlgorithm(algorithm).forecastOnADate(dateSet.get(currency), date.getLocalDate());
                res.put(currency, Collections.singletonList(rate));
            } else {
                List<Rate> rates = getAlgorithm(algorithm).forecastToPeriod(dateSet.get(currency), date.getPeriod());
                res.put(currency, rates);
            }
        }
        return res;
    }

    private ForecastAlgorithm getAlgorithm(Algorithm algorithm) {
        if (!factory.containsKey(algorithm)) {
            String message = String.format("Unsupported algorithm = %s for Forecast!", algorithm);
            logger.error(message);
            throw new UnsupportedAlgorithmException(message);
        }
        return factory.get(algorithm);
    }
}
