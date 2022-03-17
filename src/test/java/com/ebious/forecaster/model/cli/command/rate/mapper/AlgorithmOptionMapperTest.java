package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.exception.OptionMapperException;
import com.ebious.forecaster.model.domain.enums.Algorithm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class AlgoOptionMapperTest {

    OptionMapper<Algorithm> optionMapper = new AlgoOptionMapper();

    @Test
    void happyCase() {
        Option option = new Option("-alg", "linear");
        Algorithm algo = optionMapper.map(option);
        Assertions.assertThat(algo).isEqualTo(Algorithm.LINEAR);
    }

    @Test
    void badCaseInvalidOptionName() {
        Option option = new Option("-asd", "linear");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseInvalidOptionValue() {
        Option option = new Option("-alg", "absidjas");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseUpperCaseOptionName() {
        Option option = new Option("-ALG", "linear");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseUpperCaseOptionValue() {
        Option option = new Option("-alg", "LINEAR");
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