package com.ebious.forecaster.model.domain.entity;

import java.time.LocalDate;

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

        if (date != null ? !date.equals(rate.date) : rate.date != null) return false;
        return curs != null ? curs.equals(rate.curs) : rate.curs == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (curs != null ? curs.hashCode() : 0);
        return result;
    }
}


