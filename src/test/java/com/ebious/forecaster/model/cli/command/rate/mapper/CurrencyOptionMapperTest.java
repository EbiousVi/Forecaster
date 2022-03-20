package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.exception.OptionMapperException;
import com.ebious.forecaster.model.domain.enums.Currency;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CurrencyOptionMapperTest {

    final OptionMapper<List<Currency>> optionMapper = new CurrencyOptionMapper();
    final String validOptionName = "rate";
    final String validOptionValue = "USD";
    final String validOptionValues = "USD,EUR";
    final String invalidOptionName = "invalid";
    final String invalidOptionValue = "invalid";
    final String invalidOptionValues = "USD,EUR,TRY,USD,EUR,TRY";


    @Test
    void happyCaseSingleCurrency() {
        Option option = new Option(validOptionName, validOptionValue);
        List<Currency> expected = Collections.singletonList(Currency.USD);
        List<Currency> currencies = optionMapper.map(option);
        assertThat(currencies).hasSize(1).isEqualTo(expected);
    }

    @Test
    void happyCaseMultiCurrencies() {
        Option option = new Option(validOptionName, validOptionValues);
        List<Currency> expected = Arrays.asList(Currency.USD, Currency.EUR);
        List<Currency> currencies = optionMapper.map(option);
        assertThat(currencies).hasSize(2).isEqualTo(expected);
    }

    @Test
    void badCaseInvalidOptionName() {
        Option option = new Option(invalidOptionName, validOptionName);
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseInvalidSingleOptionValue() {
        Option option = new Option(validOptionName, invalidOptionValue);
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseInvalidMultiOptionValues() {
        Option option = new Option(validOptionName, invalidOptionValues);
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseLowerCaseOptionValue() {
        Option option = new Option(validOptionName, validOptionValue.toLowerCase());
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseUpperCaseOptionName() {
        Option option = new Option(validOptionName.toUpperCase(), validOptionValue);
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseEmptyOption() {
        Option option = new Option("", "");
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }
}