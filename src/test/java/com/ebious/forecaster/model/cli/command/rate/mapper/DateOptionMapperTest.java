package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.exception.OptionMapperException;
import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DateOptionMapperTest {

    final OptionMapper<ForecastDate> optionMapper = new DateOptionMapper();
    final String validDateOptionName = "-date";
    final String validDateOptionValue = "22.03.2022";
    final String validPeriodOptionName = "-period";
    final String validPeriodOptionValue = "week";
    final String invalidOptionName = "invalid";
    final String invalidOptionValue = "invalid";

    @Test
    void happyCaseDateOption() {
        Option option = new Option(validDateOptionName, validDateOptionValue);
        ForecastDate expected = new ForecastDate(LocalDate.parse(validDateOptionValue, DateUtils.PARSE_FORMATTER), Period.DATE);
        ForecastDate date = optionMapper.map(option);
        assertThat(date).isEqualTo(expected);
    }

    @Test
    void happyCasePeriodOption() {
        Option option = new Option(validPeriodOptionName, validPeriodOptionValue);
        ForecastDate expected = new ForecastDate(LocalDate.now(), Period.WEEK);
        ForecastDate date = optionMapper.map(option);
        assertThat(date).isEqualTo(expected);
    }

    @Test
    void badCaseDateOptionInvalidName() {
        Option option = new Option(invalidOptionName, validDateOptionName);
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseCrossDateOptionValidPeriodValue() {
        Option option = new Option(validDateOptionName, validPeriodOptionValue);
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseCrossPeriodOptionValidDateValue() {
        Option option = new Option(validPeriodOptionName, validDateOptionValue);
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseDateOptionInvalidValue() {
        Option option = new Option(validDateOptionName, "00.00.0000");
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionInvalidName() {
        Option option = new Option(invalidOptionName, "week");
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionInvalidValue() {
        Option option = new Option("-period", invalidOptionValue);
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseDateOptionUpperCaseName() {
        Option option = new Option(validDateOptionName.toUpperCase(), "22.03.2022");
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionUpperCaseName() {
        Option option = new Option(validPeriodOptionName.toUpperCase(), "week");
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionUpperCaseValue() {
        Option option = new Option(validPeriodOptionName, validPeriodOptionValue.toUpperCase());
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseDateOptionEmpty() {
        Option option = new Option("", "");
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCasePeriodOptionEmpty() {
        Option option = new Option("", "");
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }
}