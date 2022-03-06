package com.ebious.forecaster.forecast.domain.dataset;

import java.util.Objects;

public class CSVDataSetObject {

    private final String date;
    private final String curs;
    private final String cdx;

    public CSVDataSetObject(String date, String curs, String cdx) {
        this.date = date;
        this.curs = curs;
        this.cdx = cdx;
    }

    public String getDate() {
        return date;
    }

    public String getCurs() {
        return curs;
    }

    public String getCdx() {
        return cdx;
    }

    @Override
    public String toString() {
        return "CSVDataSetObject{" +
                "date='" + date + '\'' +
                ", curs='" + curs + '\'' +
                ", cdx='" + cdx + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CSVDataSetObject that = (CSVDataSetObject) o;
        return Objects.equals(date, that.date) && Objects.equals(curs, that.curs) && Objects.equals(cdx, that.cdx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, curs, cdx);
    }
}
