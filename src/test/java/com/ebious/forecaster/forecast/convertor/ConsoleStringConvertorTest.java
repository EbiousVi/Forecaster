package com.ebious.forecaster.forecast.convertor;

import com.ebious.forecaster.forecast.TestData;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConsoleStringConvertorTest {

    ConsoleStringConvertor consoleStringWrapper = new ConsoleStringConvertor();

    @Test
    void wrap() {
        String expected = "Вт 01.02.2022 - 77,25";
        String wrapToString = consoleStringWrapper.wrap(Collections.singletonList(TestData.forecastDay1));
        assertThat(wrapToString).isEqualTo(expected);
    }
}