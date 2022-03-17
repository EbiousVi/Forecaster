package com.ebious.forecaster.model.domain.enums;

import com.ebious.forecaster.model.exception.UnsupportedGraphColorException;

public enum GraphColor {
    GREEN(Currency.USD),
    BLUE(Currency.EUR),
    RED(Currency.AMD),
    BLACK(Currency.TRY),
    ORANGE(Currency.BGN);

    public final Currency currency;

    GraphColor(Currency currency) {
        this.currency = currency;
    }

    public static String getColor(Currency currency) {
        GraphColor[] values = values();
        for (GraphColor color : values) {
            if (color.currency.equals(currency)) {
                return color.name().toLowerCase();
            }
        }
        throw new UnsupportedGraphColorException("No match graph color to selected currency = " + currency);
    }
}
