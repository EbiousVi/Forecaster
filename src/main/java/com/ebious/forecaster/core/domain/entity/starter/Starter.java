package com.ebious.forecaster.core.domain.entity.starter;

import com.ebious.forecaster.core.domain.enums.Algorithm;
import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.core.domain.enums.Output;
import com.ebious.forecaster.core.exception.StarterBuilderException;

import java.util.List;

public class Starter {

    private List<Currency> currencies;
    private Long epochDay;
    private Algorithm algorithm;
    private Output output;

    private Starter() {
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public Long getEpochDay() {
        return epochDay;
    }

    public Algorithm getAlgoType() {
        return algorithm;
    }

    public Output getOutputType() {
        return output;
    }

    @Override
    public String toString() {
        return "Starter{" +
                "currencies=" + currencies +
                ", epochDay=" + epochDay +
                ", algoType=" + algorithm +
                ", outputType=" + output +
                '}';
    }

    public static class Builder {
        private final Starter starter;

        public Builder() {
            this.starter = new Starter();
        }

        public Builder currency(List<Currency> currencies) {
            starter.currencies = currencies;
            return this;
        }

        public Builder epochDay(Long epochDay) {
            starter.epochDay = epochDay;
            return this;
        }

        public Builder algoType(Algorithm type) {
            starter.algorithm = type;
            return this;
        }

        public Builder outputType(Output type) {
            starter.output = type;
            return this;
        }

        public Starter build() {
            if (starter.currencies == null) throw new StarterBuilderException("Currencies value is null");
            if (starter.epochDay == null) throw new StarterBuilderException("Epoch day value is null");
            if (starter.algorithm == null) starter.algorithm = Algorithm.DEFAULT;
            if (starter.output == null) starter.output = Output.DEFAULT;
            System.out.println(starter);
            return starter;
        }
    }
}
