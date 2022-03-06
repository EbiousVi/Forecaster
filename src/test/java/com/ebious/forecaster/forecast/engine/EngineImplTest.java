package com.ebious.forecaster.forecast.engine;

import com.ebious.forecaster.forecast.TestData;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.*;
import static org.assertj.core.api.Assertions.assertThat;

class EngineImplTest {

    Engine engine = new EngineImpl();

    @Test
    void startStarterTomorrow() {
        String exchangeRateValue = "77,98";
        String rateDate = now().plusDays(1).format(DateTimeFormatter.ofPattern("EE dd.MM.yyyy"));
        String separator = " - ";
        Object result = engine.start(TestData.starterTomorrow);
        assertThat(result).isEqualTo(rateDate + separator + exchangeRateValue);
    }

    @Test
    void startStarterWeek() {
        Object start = engine.start(TestData.starterWeek);
        assertThat((String) start).contains("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс");
    }
}