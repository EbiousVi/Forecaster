package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.exception.OptionMapperException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OutputOptionMapperTest {

    final OptionMapper<Output> optionMapper = new OutputOptionMapper();
    final String validOptionName = "-output";
    final String validOptionValue = "list";
    final String invalidOptionName = "invalid";
    final String invalidOptionValue = "invalid";

    @Test
    void happyCase() {
        Option option = new Option(validOptionName, validOptionValue);
        Output o = optionMapper.map(option);
        assertThat(o).isEqualTo(Output.LIST);
    }

    @Test
    void happyCaseEmptyOption() {
        Option option = new Option("", "");
        Output o = optionMapper.map(option);
        assertThat(o).isEqualTo(Output.DEFAULT);
    }

    @Test
    void badCaseInvalidOptionName() {
        Option option = new Option(invalidOptionName, validOptionValue);
        assertThatThrownBy(() -> optionMapper.map(option)).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseInvalidOptionValue() {
        Option option = new Option(validOptionName, invalidOptionValue);
        assertThatThrownBy(() -> {
            Output map = optionMapper.map(option);
            System.out.println(map);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseUpperCaseOptionName() {
        Option option = new Option(validOptionName.toUpperCase(), validOptionValue);
        assertThatThrownBy(() -> {
            Output map = optionMapper.map(option);
            System.out.println(map);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseUpperCaseOptionValue() {
        Option option = new Option(validOptionName, validOptionValue.toUpperCase());
        assertThatThrownBy(() -> {
            Output map = optionMapper.map(option);
            System.out.println(map);
        }).isInstanceOf(OptionMapperException.class);
    }
}