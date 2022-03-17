package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.cli.exception.OptionMapperException;
import com.ebious.forecaster.model.domain.enums.Currency;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class CurrencyOptionMapperTest {
    OptionMapper<List<Currency>> optionMapper = new CurrencyOptionMapper();

    @Test
    void happyCaseSingleCurrency() {
        Option option = new Option("rate", "USD");
        List<Currency> expected = Collections.singletonList(Currency.USD);
        List<Currency> currencies = optionMapper.map(option);
        assertThat(currencies).hasSize(1).isEqualTo(expected);
    }

    @Test
    void happyCaseMultiCurrencies() {
        Option option = new Option("rate", "USD,EUR");
        List<Currency> expected = Arrays.asList(Currency.USD, Currency.EUR);

        List<Currency> currencies = optionMapper.map(option);
        assertThat(currencies).hasSize(2).isEqualTo(expected);
    }

    @Test
    void badCaseInvalidOptionName() {
        Option option = new Option("asd", "USD");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseInvalidSingleOptionValue() {
        Option option = new Option("rate", "US");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseInvalidMultiOptionValues() {
        Option option = new Option("rate", "USD,XX");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseLowerCaseOptionValue() {
        Option option = new Option("rate", "usd");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseUpperCaseOptionName() {
        Option option = new Option("RATE", "usd");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseEmptyOption() {
        Option option = new Option("", "");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }
}