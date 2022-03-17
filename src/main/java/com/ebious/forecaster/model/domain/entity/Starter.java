package com.ebious.forecaster.model.domain.entity;

import com.ebious.forecaster.model.cli.OptionMapper;
import com.ebious.forecaster.model.cli.domain.Option;
import com.ebious.forecaster.model.cli.command.rate.mapper.*;
import com.ebious.forecaster.model.domain.enums.Algorithm;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.exception.StarterBuilderException;

import java.util.List;

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

        if (currencies != null ? !currencies.equals(starter.currencies) : starter.currencies != null) return false;
        if (forecastDate != null ? !forecastDate.equals(starter.forecastDate) : starter.forecastDate != null)
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
        private final OptionMapper<Algorithm> algoMapping = new AlgoOptionMapper();
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

        //Может использовать проверяемое исключение для оповещения сложности создания объекта?
        public Starter build() throws StarterBuilderException {
            if (starter.currencies == null) throw new StarterBuilderException("Currencies is null");
            if (starter.forecastDate == null) throw new StarterBuilderException("Forecast date is null");
            if (starter.algorithm == null) throw new StarterBuilderException("Algorithm is null");
            if (starter.output == null) throw new StarterBuilderException("Output is null");
            System.out.println(starter);
            return starter;
        }
    }

    public static class SimpleBuilder {
        private final Starter starter;

        public SimpleBuilder() {
            this.starter = new Starter();
        }

        public SimpleBuilder currencies(List<Currency> currencies) {
            starter.currencies = currencies;
            return this;
        }

        public SimpleBuilder forecastDate(ForecastDate forecastDate) {
            starter.forecastDate = forecastDate;
            return this;
        }

        public SimpleBuilder algorithm(Algorithm algorithm) {
            starter.algorithm = algorithm;
            return this;
        }

        public SimpleBuilder output(Output output) {
            starter.output = output;
            return this;
        }

        //Может использовать проверяемое исключение для оповещения сложности создания объекта?
        public Starter build() throws StarterBuilderException {
            return starter;
        }
    }
}
