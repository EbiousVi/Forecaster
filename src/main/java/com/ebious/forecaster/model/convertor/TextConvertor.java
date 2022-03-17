package com.ebious.forecaster.model.convertor;

import com.ebious.forecaster.controller.gateway.ContentType;
import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.utils.DateUtils;
import com.ebious.forecaster.model.utils.DoubleUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TextConvertor implements Convertor {

    private static final String SEPARATOR = " - ";

    @Override
    public Response convert(Map<Currency, List<Rate>> rates) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Currency, List<Rate>> pair : rates.entrySet()) {
            builder.append(pair.getKey().name()).append(System.lineSeparator());
            builder.append(ratesToString(pair.getValue()));
        }
        return new Response(builder.toString(), ContentType.TEXT);
    }

    private String ratesToString(List<Rate> rates) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Rate rate : rates) {
            stringBuilder.append(rateToString(rate)).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private String rateToString(Rate rate) {
        return dateToString(rate.getDate()) + SEPARATOR + exchangeRateValueToString(rate.getCurs());
    }

    private String dateToString(LocalDate date) {
        return date.format(DateUtils.PRINT_FORMATTER);
    }

    private String exchangeRateValueToString(Double value) {
        return DoubleUtils.DECIMAL_FORMAT.format(value);
    }
}
