package com.ebious.forecaster.forecast.forecast;

import com.ebious.forecaster.forecast.TestData;
import com.ebious.forecaster.forecast.domain.enums.Currency;
import com.ebious.forecaster.forecast.domain.forecast.Forecast;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ForecastPrevSevenExchangeRatesTest {

    ForecastPrevSevenExchangeRates forecastPrevSevenExchangeRates = new ForecastPrevSevenExchangeRates();

    @Test
    void doForecastTomorrow() {
        Forecast expected = new Forecast(77.60714285714286D, LocalDate.now().plusDays(1), Currency.USD);
        List<Forecast> forecasts = forecastPrevSevenExchangeRates.doForecast(
                TestData.getTestCSVDataSetObject(), TestData.starterTomorrow);
        assertThat(forecasts).hasSize(1).containsExactly(expected);
    }

    @Test
    void doForecastWeek() {
        List<Forecast> forecasts = forecastPrevSevenExchangeRates.doForecast(
                TestData.getTestCSVDataSetObject(), TestData.starterWeek);
        assertThat(forecasts).hasSize(7);
    }

    @Test
    void getExchangeRateValues() {
        List<Double> expected = Arrays.asList(77.25D, 77.10D, 76.20D, 77.50D, 79.10D, 77.90, 78.20);
        List<Double> exchangeRateValues = forecastPrevSevenExchangeRates.getExchangeRateValues(TestData.getTestCSVDataSetObject());
        assertThat(exchangeRateValues).hasSize(7).isEqualTo(expected);
    }

    @Test
    void averageByLastSevenRates() {
        List<Double> exchangeRateValues = Arrays.asList(77.25D, 77.10D, 76.20D, 77.50D, 79.10D, 77.90, 78.20);
        Double expected = 77.60714285714286D;
        Double aDouble = forecastPrevSevenExchangeRates.averageByLastSevenRates(exchangeRateValues);
        assertThat(aDouble).isEqualTo(expected);
    }
}