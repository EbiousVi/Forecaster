package com.ebious.forecaster.model.cli.command.rate.mapper;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.cli.exception.OptionMapperException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OutputOptionMapperTest {
    OptionMapper<Output> optionMapper = new OutputOptionMapper();

    @Test
    void happyCase() {
        Option option = new Option("-output", "list");
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
        Option option = new Option("asd", "default");
        assertThatThrownBy(() -> {
            optionMapper.map(option);
        }).isInstanceOf(OptionMapperException.class);
    }

    @Test
    void badCaseInvalidOptionArgument() {
        Option option = new Option("-output", "asd");
        assertThatThrownBy(() -> {
            Output map = optionMapper.map(option);
            System.out.println(map);
        }).isInstanceOf(OptionMapperException.class);
    }

}