package com.ebious.forecaster.model.algorithm;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.exception.AlgorithmException;
import com.ebious.forecaster.model.suppliers.DataSetSupplier;
import com.ebious.forecaster.model.suppliers.csv.CsvDataSetSupplier;
import com.ebious.forecaster.model.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class ActualAlgorithmTest {

    final ForecastAlgorithm actual = new ActualAlgorithm();
    final DataSetSupplier supplier = new CsvDataSetSupplier();

    @Test
    void happyCaseForecastOnADate() {
        LocalDate expectedDate = LocalDate.parse("26.03.2022", DateUtils.PARSE_FORMATTER);
        Currency currency = Currency.USD;
        Map<Currency, List<Rate>> dataSet = supplier.deliverDataSet(Collections.singletonList(currency));
        Rate rate = actual.forecastOnADate(dataSet.get(currency), expectedDate);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(rate.getDate()).isEqualTo(expectedDate);
        softAssertions.assertThat(rate.getCurs()).isBetween(142D, 143D);
        softAssertions.assertAll();
    }

    @Test
    void badCaseForecastOnADateNothingFoundOnTwoYearsAgoDate() {
        LocalDate expectedDate = LocalDate.parse("22.03.2022", DateUtils.PARSE_FORMATTER);
        Currency currency = Currency.USD;
        Map<Currency, List<Rate>> dataSet = supplier.deliverDataSet(Collections.singletonList(currency));
        Assertions.assertThatThrownBy(() -> actual.forecastOnADate(dataSet.get(currency), expectedDate))
                .isInstanceOf(AlgorithmException.class)
                .hasMessage("2 years ago rate not found at Вс 22.03.2020");
    }
}