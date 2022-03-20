package com.ebious.forecaster.model.domain.entity;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.cli.command.rate.mapper.*;
import com.ebious.forecaster.model.domain.enums.Algorithm;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.exception.StarterBuildException;

import java.util.List;
import java.util.Objects;

public class Starter {

    private List<Currency> currencies;
    private ForecastDate forecastDate;
    private Algorithm algorithm;
    private Output output;

    private Starter() {
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public ForecastDate getForecastDate() {
        return forecastDate;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Output getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "Starter{" +
                "currencies=" + currencies +
                ", forecastDate=" + forecastDate +
                ", algorithm=" + algorithm +
                ", output=" + output +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Starter starter = (Starter) o;

        if (!Objects.equals(currencies, starter.currencies)) return false;
        if (!Objects.equals(forecastDate, starter.forecastDate))
            return false;
        if (algorithm != starter.algorithm) return false;
        return output == starter.output;
    }

    @Override
    public int hashCode() {
        int result = currencies != null ? currencies.hashCode() : 0;
        result = 31 * result + (forecastDate != null ? forecastDate.hashCode() : 0);
        result = 31 * result + (algorithm != null ? algorithm.hashCode() : 0);
        result = 31 * result + (output != null ? output.hashCode() : 0);
        return result;
    }

    public static class OptionBuilder {
        private final Starter starter;
        private final OptionMapper<List<Currency>> currencyMapping = new CurrencyOptionMapper();
        private final OptionMapper<ForecastDate> forecastDateMapping = new DateOptionMapper();
        private final OptionMapper<Algorithm> algoMapping = new AlgorithmOptionMapper();
        private final OptionMapper<Output> outputMapping = new OutputOptionMapper();

        public OptionBuilder() {
            this.starter = new Starter();
        }

        public OptionBuilder currencies(Option option) {
            starter.currencies = currencyMapping.map(option);
            return this;
        }

        public OptionBuilder forecastDate(Option option) {
            starter.forecastDate = forecastDateMapping.map(option);
            return this;
        }

        public OptionBuilder algorithm(Option option) {
            starter.algorithm = algoMapping.map(option);
            return this;
        }

        public OptionBuilder output(Option option) {
            starter.output = outputMapping.map(option);
            return this;
        }

        public Starter build() {
            if (starter.currencies == null)
                throw new StarterBuildException("Build failed, object must have all fields. Currencies is missing");
            if (starter.forecastDate == null)
                throw new StarterBuildException("Build failed, object must have all fields. Forecast date  is missing");
            if (starter.algorithm == null)
                throw new StarterBuildException("Build failed, object must have all fields. Algorithm is missing");
            if (starter.output == null)
                throw new StarterBuildException("Build failed, object must have all fields. Output is missing");
            return starter;
        }
    }
}
