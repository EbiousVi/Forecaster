package com.ebious.forecaster.core.forecast;

import com.ebious.forecaster.core.domain.entity.dataset.DataSet;
import com.ebious.forecaster.core.domain.entity.forecast.Forecast;
import com.ebious.forecaster.core.suppliers.DataSetSupplier;
import com.ebious.forecaster.core.suppliers.CsvDataSetSupplier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForecastActual {

    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DataSetSupplier dataSetSupplier = new CsvDataSetSupplier();

/*        LocalDate localDate = LocalDate.now().plusDays(1);
        String bound1 = localDate.minusYears(2).format(dateTimeFormatter);
        String bound2 = localDate.minusYears(3).format(dateTimeFormatter);*/
        List<Forecast> forecasts = new ArrayList<>();

        Map<LocalDate, DataSet> suka = new HashMap<>();

   /*     for (int i = 0; i < 30; i++) {
            suka.get()
        }
        Double x1 = null, x2 = null;
        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i).getDate().equals(bound1)) {
                x1 = 13D;
            }
            if (dataSet.get(i).getDate().equals(bound2)) {
                x2 = 13D;
            }
        }
        if (x1 == null || x2 == null) {
            System.out.println("EXCEPTION");
        }*/
    }
}
