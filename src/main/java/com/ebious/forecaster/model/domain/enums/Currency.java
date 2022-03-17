package com.ebious.forecaster.model.domain.enums;

import com.ebious.forecaster.model.exception.UnsupportedCurrencyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Currency {
    AMD,
    BGN,
    USD,
    EUR,
    TRY;

    public static Currency getCurrency(String name) {
        for (Currency currency : values()) {
            if (currency.name().equals(name)) return currency;
        }
        throw new UnsupportedCurrencyException("Unsupported currency for Forecast! Available values = " + Arrays.toString(values()));
    }

    public static Boolean contains(String value) {
        for (Currency currency : values()) {
            if (currency.name().equals(value)) return true;
        }
        return false;
    }

    public static List<String> getStringValues() {
        List<String> values = new ArrayList<>();
        for (Currency value : Currency.values()) {
            values.add(value.name());
        }
        return values;
    }
}
