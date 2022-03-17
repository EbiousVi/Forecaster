package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.cli.exception.OptionMapperException;
import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DateOptionMapperTest {

    OptionMapper<ForecastDate> optionMapper = new DateOptionMapper();

    @Test
    void happyCaseDateOption() {
        Option option = new Option("-date", "22.03.2022");
        ForecastDate expected = new ForecastDate(LocalDate.parse("22.03.2022", DateUtils.PARSE_FORMATTER), Period.NONE);
        ForecastDate date = optionMapper.map(option);
        assertThat(date).isEqualTo(expected);
    }

    @Test
    void happyCasePeriodOption() {
        Option option = new Option("-period", "week");
        ForecastDate expected = new ForecastDate(LocalDate.now(), Period.WEEK);
        ForecastDate date = optionMapper.map(option);
        assertThat(date).isEqualTo(expected);
    }

    @Test
    void badCaseDateOptionInvalidName() {
        Option option = new Option("-asd", "22.03.2022");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseDateOptionInvalidValue() {
        Option option = new Option("-date", "00.00.0000");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionInvalidName() {
        Option option = new Option("-asd", "week");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionInvalidValue() {
        Option option = new Option("-period", "foo");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseDateOptionUpperCaseName() {
        Option option = new Option("-DATE", "22.03.2022");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionUpperCaseName() {
        Option option = new Option("-PERIOD", "week");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionUpperCaseValue() {
        Option option = new Option("-period", "WEEK");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseDateOptionEmpty() {
        Option option = new Option("", "");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionEmpty() {
        Option option = new Option("", "");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }
}