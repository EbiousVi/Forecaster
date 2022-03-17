package com.ebious.forecaster.model.engine;

import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.domain.entity.Starter;
import org.junit.jupiter.api.Test;

class ForecastEngineImplTest {

    ForecastEngine forecastEngine = new ForecastEngineImpl();

    @Test
    void executeCurUSDDateAlgAvgOutputList() {
        Starter starter = new Starter.OptionBuilder()
                .currencies(new Option("rate", "USD"))
                .forecastDate(new Option("-date", "22.03.2022"))
                .algorithm(new Option("-alg", "avg"))
                .output(new Option("-output", "list"))
                .build();
        Response execute = forecastEngine.execute(starter);
        System.out.println(execute.getBody());
    }

    @Test
    void executeCurUSDDateAlgAvgOutputGraph() {
        Starter starter = new Starter.OptionBuilder()
                .currencies(new Option("rate", "USD"))
                .forecastDate(new Option("-date", "22.03.2022"))
                .algorithm(new Option("-alg", "avg"))
                .output(new Option("-output", "graph"))
                .build();
        Response execute = forecastEngine.execute(starter);
        System.out.println(execute.getBody());
    }
}