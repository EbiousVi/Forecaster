package com.ebious.forecaster.forecast.domain;

import com.ebious.forecaster.forecast.domain.enums.Currency;
import com.ebious.forecaster.forecast.domain.enums.Interval;
import com.ebious.forecaster.forecast.domain.enums.ConvertorType;

public class Starter {

    private final Currency currency;
    private final Interval interval;
    private final ConvertorType convertorType;

    public Starter(Currency currency, Interval interval, ConvertorType convertorType) {
        this.currency = currency;
        this.interval = interval;
        this.convertorType = convertorType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Interval getInterval() {
        return interval;
    }

    public ConvertorType getOutputType() {
        return convertorType;
    }

    @Override
    public String toString() {
        return "Starter{" +
                "currency=" + currency +
                ", interval=" + interval +
                ", outputType=" + convertorType +
                '}';
    }
}
