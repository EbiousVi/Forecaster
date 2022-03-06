package com.ebious.forecaster.forecast.convertor;

import com.ebious.forecaster.forecast.domain.forecast.Forecast;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleStringConvertor implements Convertor<String> {

    private final static String separator = " - ";
    private final static String delimiter = "\n";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EE dd.MM.yyyy");
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    public String wrap(List<Forecast> forecasts) {
        return forecasts.stream()
                .map(this::forecastToString)
                .collect(Collectors.joining(delimiter));
    }

    private String forecastToString(Forecast forecast) {
        return dateToString(forecast.getDate()) + separator + exchangeRateValueToString(forecast.getValue());
    }

    private String dateToString(LocalDate date) {
        return date.format(dateFormatter);
    }

    private String exchangeRateValueToString(Double value) {
        return decimalFormat.format(value);
    }
}
