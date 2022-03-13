package com.ebious.forecaster.core.domain.entity.dataset;

import java.time.LocalDate;
import java.util.Objects;

public class DataSet {

    private final String nominal;
    private final LocalDate date;
    private final Double curs;
    private final String cdx;

    public DataSet(String nominal, LocalDate date, Double curs, String cdx) {
        this.nominal = nominal;
        this.date = date;
        this.curs = curs;
        this.cdx = cdx;
    }

    public String getNominal() {
        return nominal;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getCurs() {
        return curs;
    }

    public String getCdx() {
        return cdx;
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "nominal='" + nominal + '\'' +
                ", date=" + date +
                ", curs=" + curs +
                ", cdx='" + cdx + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSet dataSet = (DataSet) o;

        if (!Objects.equals(nominal, dataSet.nominal)) return false;
        if (!Objects.equals(date, dataSet.date)) return false;
        if (!Objects.equals(curs, dataSet.curs)) return false;
        return Objects.equals(cdx, dataSet.cdx);
    }

    @Override
    public int hashCode() {
        int result = nominal != null ? nominal.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (curs != null ? curs.hashCode() : 0);
        result = 31 * result + (cdx != null ? cdx.hashCode() : 0);
        return result;
    }
}
