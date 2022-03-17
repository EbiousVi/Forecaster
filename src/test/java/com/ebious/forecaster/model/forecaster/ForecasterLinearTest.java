package com.ebious.forecaster.model.forecaster;

import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.suppliers.DataSetSupplier;
import com.ebious.forecaster.model.suppliers.csv.CsvDataSetSupplier;
import com.ebious.forecaster.model.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;


class ForecasterLinearTest {
    Forecaster forecaster = new ForecasterLinear();
    DataSetSupplier dataSetSupplier = new CsvDataSetSupplier();

    @Test
    void linearForecasterByPeriod() {
        ForecastDate period = new ForecastDate(LocalDate.now(), Period.WEEK);
        Map<Currency, List<Rate>> dataSet = dataSetSupplier.getDataSet(Collections.singletonList(Currency.USD));
        Map<Currency, List<Rate>> rates = forecaster.forecast(dataSet, period);
        rates.forEach((k, v) ->
        {
            System.out.println(k);
            v.forEach(System.out::println);
        });
    }

    @Test
    void linearForecasterByDate() {
        ForecastDate date = new ForecastDate(LocalDate.parse("30.03.2022", DateUtils.PARSE_FORMATTER), Period.NONE);
        Map<Currency, List<Rate>> dataSet = dataSetSupplier.getDataSet(Collections.singletonList(Currency.USD));
        Map<Currency, List<Rate>> rates = forecaster.forecast(dataSet, date);
        rates.forEach((k, v) ->
        {
            System.out.println(k);
            v.forEach(System.out::println);
        });
    }
}