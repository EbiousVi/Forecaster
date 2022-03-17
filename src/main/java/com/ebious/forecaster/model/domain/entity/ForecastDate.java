package com.ebious.forecaster.model.domain.entity;

import com.ebious.forecaster.model.domain.enums.Period;

import java.time.LocalDate;


public class ForecastDate {
    private final LocalDate localDate;
    private final Period period;

    public ForecastDate(LocalDate localDate, Period period) {
        this.localDate = localDate;
        this.period = period;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public Period getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return "ForecastDate{" +
                "date=" + localDate +
                ", period=" + period +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForecastDate that = (ForecastDate) o;

        if (localDate != null ? !localDate.equals(that.localDate) : that.localDate != null) return false;
        return period == that.period;
    }

    @Override
    public int hashCode() {
        int result = localDate != null ? localDate.hashCode() : 0;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        return result;
    }
}
