package com.ebious.forecaster.core.convertor;

import com.ebious.forecaster.core.domain.entity.forecast.Forecast;
import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.core.domain.enums.Output;
import com.ebious.forecaster.controller.gateway.Response;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ConvertorFactory {

    private static final Map<Output, Convertor> convertorFactory = new EnumMap<>(Output.class);

    static {
        convertorFactory.put(Output.DEFAULT, new TextConvertor());
        convertorFactory.put(Output.LIST, new TextConvertor());
        convertorFactory.put(Output.GRAPH, new GraphConvertor());
    }

    public Response convert(Map<Currency, List<Forecast>> map, Output type) {
        if (convertorFactory.containsKey(type)) {
            return  convertorFactory.get(type).wrapToResponse(map);
        } else {
            throw new IllegalArgumentException("Illegal output type");
        }
    }
}
