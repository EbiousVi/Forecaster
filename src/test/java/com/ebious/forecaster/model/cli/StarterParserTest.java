package com.ebious.forecaster.model.cli;

import com.ebious.forecaster.model.cli.command.rate.parser.StarterParser;
import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.entity.Starter;
import com.ebious.forecaster.model.domain.enums.Algorithm;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class StarterStarterParserTest {

    StarterParser starterStarterParser = new StarterParser();
    List<Currency> currencies = Arrays.asList(Currency.USD);
    ForecastDate date = new ForecastDate(LocalDate.parse("30.03.2022", DateUtils.PARSE_FORMATTER), Period.DATE);
    ForecastDate period = new ForecastDate(LocalDate.now(), Period.WEEK);
    Algorithm algorithm = Algorithm.MOON;
    Output output = Output.DEFAULT;
/*    Starter expected = new Starter.PlayOldBuilder()
            .currencies(currencies)
            .forecastDate(date)
            .algorithm(algorithm)
            .output(output)
            .build();*/

    @Test
    void parseStarterCase1() {
        Starter parse = starterStarterParser.parse("rate USD -date 30.03.2022 -alg asd");
        System.out.println(parse);
    }
}