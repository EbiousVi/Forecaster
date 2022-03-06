package com.ebious.forecaster.forecast.domain.enums;

import com.ebious.forecaster.forecast.exception.UnsupportedCurrencyException;

import java.util.Arrays;

public enum Currency {
    USD("Доллар США"),
    EUR("Евро"),
    TRY("Турецкая Лира");

    public final String value;

    Currency(String value) {
        this.value = value;
    }

    public static Currency getCurrency(String value) throws UnsupportedCurrencyException {
        for (Currency currency : values()) {
            if (currency.name().equals(value)) return currency;
        }
        throw new UnsupportedCurrencyException(
                "Unsupported Currency for Forecast! Available values = " + Arrays.toString(values()));
    }

}
