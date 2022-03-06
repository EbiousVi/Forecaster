package com.ebious.forecaster.forecast.domain.forecast;

import com.ebious.forecaster.forecast.domain.enums.Currency;

import java.time.LocalDate;
import java.util.Objects;

public class Forecast {
    private Double value;
    private LocalDate date;
    private Currency currency;

    public Forecast(Double value, LocalDate date, Currency currency) {
        this.value = value;
        this.date = date;
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
