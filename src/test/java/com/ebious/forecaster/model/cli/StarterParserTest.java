package com.ebious.forecaster.model.cli;

import com.ebious.forecaster.model.cli.command.rate.parser.StarterParser;
import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.entity.Starter;
import com.ebious.forecaster.model.domain.enums.Algorithm;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class StarterParserTest {

    final String currenciesField = "currencies";
    final String forecastDateField = "forecastDate";
    final String algorithmField = "algorithm";
    final String outputField = "output";
    final StarterParser starterStarterParser = new StarterParser();
    final ForecastDate expectedDate = new ForecastDate(LocalDate.parse("26.03.2022", DateUtils.PARSE_FORMATTER), Period.DATE);
    final ForecastDate expectedPeriod = new ForecastDate(LocalDate.now(), Period.WEEK);

    @Test
    void happyCaseFirstThreeRequiredOption_USD_DATE_MOON() {
        String commandLine = "rate USD -date 26.03.2022 -alg moon";
        Starter parse = starterStarterParser.parse(commandLine);
        assertThat(parse)
                .hasFieldOrPropertyWithValue(currenciesField, Arrays.asList(Currency.USD))
                .hasFieldOrPropertyWithValue(forecastDateField, expectedDate)
                .hasFieldOrPropertyWithValue(algorithmField, Algorithm.MOON)
                .hasFieldOrPropertyWithValue(outputField, Output.DEFAULT);
    }

    @Test
    void happyCaseFirstAllOptions_EUR_DATE_LINEAR_LIST() {
        String commandLine = "rate EUR -date 26.03.2022 -alg linear -output list";
        Starter parse = starterStarterParser.parse(commandLine);
        assertThat(parse)
                .hasFieldOrPropertyWithValue(currenciesField, Arrays.asList(Currency.EUR))
                .hasFieldOrPropertyWithValue(forecastDateField, expectedDate)
                .hasFieldOrPropertyWithValue(algorithmField, Algorithm.LINEAR)
                .hasFieldOrPropertyWithValue(outputField, Output.LIST);
    }

    @Test
    void happyCaseFirstAllOptions_EUR_USD_WEEK_AVG_GRAPH() {
        String commandLine = "rate EUR,USD -period week -alg avg -output graph";
        Starter parse = starterStarterParser.parse(commandLine);
        assertThat(parse)
                .hasFieldOrPropertyWithValue(currenciesField, Arrays.asList(Currency.EUR, Currency.USD))
                .hasFieldOrPropertyWithValue(forecastDateField, expectedPeriod)
                .hasFieldOrPropertyWithValue(algorithmField, Algorithm.AVG)
                .hasFieldOrPropertyWithValue(outputField, Output.GRAPH);
    }
}