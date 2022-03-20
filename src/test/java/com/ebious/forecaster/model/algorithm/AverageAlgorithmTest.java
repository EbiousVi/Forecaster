package com.ebious.forecaster.model.algorithm;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.suppliers.DataSetSupplier;
import com.ebious.forecaster.model.suppliers.csv.CsvDataSetSupplier;
import com.ebious.forecaster.model.utils.DateUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class AverageAlgorithmTest {

    final ForecastAlgorithm average = new AverageAlgorithm();
    final DataSetSupplier supplier = new CsvDataSetSupplier();

    @Test
    void forecastOnADate() {
        LocalDate expectedDate = LocalDate.parse("30.03.2022", DateUtils.PARSE_FORMATTER);
        Currency currency = Currency.USD;
        Map<Currency, List<Rate>> dataSet = supplier.deliverDataSet(Arrays.asList(currency));
        Rate rate = average.forecastOnADate(dataSet.get(currency), expectedDate);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(rate.getDate()).isEqualTo(expectedDate);
        softAssertions.assertThat(rate.getCurs()).isBetween(92D, 93D);
        softAssertions.assertAll();
    }

    @Test
    void forecastToPeriod() {
        Period period = Period.WEEK;
        Currency currency = Currency.USD;
        Map<Currency, List<Rate>> dataSet = supplier.deliverDataSet(Arrays.asList(currency));
        List<Rate> rates = average.forecastToPeriod(dataSet.get(currency), period);
        assertSoftly(
                softAssertions -> {
                    for (int i = 0, daysCount = 1; i < rates.size(); i++, daysCount++) {
                        softAssertions.assertThat(rates.get(i).getDate()).isEqualTo(LocalDate.now().plusDays(daysCount));
                        softAssertions.assertThat(rates.get(i).getCurs()).isBetween(90D, 98D);
                    }
                }
        );
    }
}