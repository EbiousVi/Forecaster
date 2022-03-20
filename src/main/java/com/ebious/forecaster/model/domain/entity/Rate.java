package com.ebious.forecaster.model.domain.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Rate {

    private final LocalDate date;
    private final Double curs;

    public Rate(LocalDate date, Double curs) {
        this.curs = curs;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getCurs() {
        return curs;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "date=" + date +
                ", curs=" + curs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate = (Rate) o;

        if (!Objects.equals(date, rate.date)) return false;
        return Objects.equals(curs, rate.curs);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (curs != null ? curs.hashCode() : 0);
        return result;
    }
}


