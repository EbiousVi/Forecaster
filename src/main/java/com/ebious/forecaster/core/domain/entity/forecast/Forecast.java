package com.ebious.forecaster.core.domain.entity.forecast;

import com.ebious.forecaster.core.domain.enums.Currency;

import java.time.LocalDate;
import java.util.Objects;

public class Forecast {

    private final Double value;
    private final LocalDate date;
    private final Currency currency;

    public Forecast(Double value, LocalDate date, Currency currency) {
        this.value = value;
        this.date = date;
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "value=" + value +
                ", date=" + date +
                ", currency=" + currency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Forecast forecast = (Forecast) o;

        if (!Objects.equals(value, forecast.value)) return false;
        if (!Objects.equals(date, forecast.date)) return false;
        return currency == forecast.currency;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
