package com.ebious.forecaster.model.engine;

import com.ebious.forecaster.model.convertor.Convertor;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.entity.Starter;
import com.ebious.forecaster.model.convertor.ConvertorFactory;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.forecaster.Forecaster;
import com.ebious.forecaster.model.forecaster.ForecasterFactory;
import com.ebious.forecaster.model.suppliers.DataSetProducer;
import com.ebious.forecaster.controller.gateway.Response;

import java.util.List;
import java.util.Map;

public class ForecastEngineImpl implements ForecastEngine {

    private final DataSetProducer dataSetProducer = new DataSetProducer();
    private final ForecasterFactory forecasterFactory = new ForecasterFactory();
    private final ConvertorFactory convertorFactory = new ConvertorFactory();

    @Override
    public Response execute(Starter starter) {
        Map<Currency, List<Rate>> dataSet = dataSetProducer.getDataSet(starter.getCurrencies());
        Forecaster forecaster = forecasterFactory.getForecaster(starter.getAlgorithm());
        Map<Currency, List<Rate>> rates = forecaster.forecast(dataSet, starter.getForecastDate());
        Convertor convertor = convertorFactory.getConvertor(starter.getOutput());
        return convertor.convert(rates);
    }
}