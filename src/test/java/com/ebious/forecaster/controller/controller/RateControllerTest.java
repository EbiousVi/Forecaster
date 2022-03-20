package com.ebious.forecaster.controller.controller;

import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.controller.gateway.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class RateControllerTest {

    @Test
    void printAllCases() {
        String dateLinear = "rate USD -date 26.03.2022 -alg linear";
        String dateActual = "rate USD -date 26.03.2022 -alg actual";
        String dateAvg = "rate USD -date 26.03.2022 -alg avg";
        String dateMoon = "rate USD -date 26.03.2022 -alg moon";

        String tomorrowLinear = "rate USD -period tomorrow -alg linear";
        String tomorrowActual = "rate USD -period tomorrow -alg actual";
        String tomorrowAvg = "rate USD -period tomorrow -alg avg";
        String tomorrowMoon = "rate USD -period tomorrow -alg moon";

        String weekLinear = "rate USD -period week -alg linear";
        String weekActual = "rate USD -period week -alg actual";
        String weekAvg = "rate USD -period week -alg avg";
        String weekMoon = "rate USD -period week -alg moon";

        List<String> queries = Arrays.asList(
                dateLinear, dateActual, dateAvg, dateMoon,
                tomorrowLinear, tomorrowActual, tomorrowAvg, tomorrowMoon,
                weekLinear, weekActual, weekAvg, weekMoon
        );
        RateController rateController = new RateController();
        for (String query : queries) {
            try {
                System.out.println(query);
                Response response = rateController.perform(new Request(query));
                System.out.println(response.getBody());
            } catch (Exception e) {
                System.out.println(e.getMessage() + System.lineSeparator());
            }
        }
    }
}