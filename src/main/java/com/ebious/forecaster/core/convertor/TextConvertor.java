package com.ebious.forecaster.core.convertor;

import com.ebious.forecaster.controller.gateway.ContentType;
import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.core.domain.entity.forecast.Forecast;
import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.core.utils.DateUtils;
import com.ebious.forecaster.core.utils.DoubleUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class TextConvertor implements Convertor {
    private static final String SEPARATOR = " - ";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    public Response wrapToResponse(Map<Currency, List<Forecast>> forecasts) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Currency, List<Forecast>> pair : forecasts.entrySet()) {
            builder.append(pair.getKey().name()).append(LINE_SEPARATOR);
            builder.append(forecastsToString(pair.getValue()));
        }
        return new Response(builder.toString(), ContentType.TEXT);
    }

    private String forecastsToString(List<Forecast> forecasts) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Forecast forecast : forecasts) {
            stringBuilder.append(forecastToString(forecast)).append(LINE_SEPARATOR);
        }
        return stringBuilder.toString();
    }

    private String forecastToString(Forecast forecast) {
        return dateToString(forecast.getDate()) + SEPARATOR + exchangeRateValueToString(forecast.getValue());
    }

    private String dateToString(LocalDate date) {
        return date.format(DateUtils.PRINT_FORMATTER);
    }

    private String exchangeRateValueToString(Double value) {
        return DoubleUtils.DECIMAL_FORMAT.format(value);
    }
}
