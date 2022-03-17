package com.ebious.forecaster.model.forecaster;

import com.ebious.forecaster.model.domain.enums.Algorithm;
import com.ebious.forecaster.model.exception.UnsupportedAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;

public final class ForecasterFactory {

    private static final Logger logger = LoggerFactory.getLogger(ForecasterFactory.class);
    private final Map<Algorithm, Forecaster> factory = new EnumMap<>(Algorithm.class);

    {
        factory.put(Algorithm.LINEAR, new ForecasterLinear());
        factory.put(Algorithm.MOON, new ForecasterMoon());
        factory.put(Algorithm.ACTUAL, new ForecasterActual());
        factory.put(Algorithm.AVG, new ForecasterAverage());
    }

    public Forecaster getForecaster(Algorithm algorithm) {
        if (factory.containsKey(algorithm)) {
            return factory.get(algorithm);
        } else {
            String errMsg = String.format("Unsupported algorithm = %s for Forecast!", algorithm);
            logger.error(errMsg);
            throw new UnsupportedAlgorithmException(errMsg);
        }
    }
}
