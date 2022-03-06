package com.ebious.forecaster.forecast.convertor;

import com.ebious.forecaster.forecast.domain.forecast.Forecast;
import com.ebious.forecaster.forecast.domain.enums.ConvertorType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ConvertorFactory {

    private static final Map<ConvertorType, Convertor<?>> convertorFactory = new EnumMap<>(ConvertorType.class);

    static {
        convertorFactory.put(ConvertorType.CONSOLE, new ConsoleStringConvertor());
    }

    public Object wrap(List<Forecast> forecasts, ConvertorType type) {
        if (convertorFactory.containsKey(type)) {
            return convertorFactory.get(type).wrap(forecasts);
        } else {
            throw new IllegalArgumentException("Illegal wrapper type!");
        }
    }
}
