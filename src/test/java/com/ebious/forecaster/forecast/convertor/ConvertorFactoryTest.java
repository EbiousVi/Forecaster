package com.ebious.forecaster.forecast.convertor;

import com.ebious.forecaster.forecast.TestData;
import com.ebious.forecaster.forecast.domain.forecast.Forecast;
import com.ebious.forecaster.forecast.domain.enums.ConvertorType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;


class ConvertorFactoryTest {

    ConvertorFactory convertorFactory = new ConvertorFactory();

    @Test
    void wrap() {
        String expected = "Вт 01.02.2022 - 77,25";
        List<Forecast> singleForecast = Collections.singletonList(TestData.forecastDay1);
        Object wrap = convertorFactory.wrap(singleForecast, ConvertorType.CONSOLE);
        Assertions.assertThat(wrap).isEqualTo(expected);
    }
}