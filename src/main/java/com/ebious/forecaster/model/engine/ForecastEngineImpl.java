package com.ebious.forecaster.model.engine;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.entity.Starter;
import com.ebious.forecaster.model.convertor.ConvertorFactory;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.forecaster.Forecaster;
import com.ebious.forecaster.model.forecaster.ForecasterImpl;
import com.ebious.forecaster.model.repository.DataSetRepository;
import com.ebious.forecaster.controller.gateway.Response;

import java.util.List;
import java.util.Map;

public class ForecastEngineImpl implements ForecastEngine {

    private final DataSetRepository dataSetRepository = new DataSetRepository();
    private final Forecaster forecaster = new ForecasterImpl();
    private final ConvertorFactory convertorFactory = new ConvertorFactory();

    @Override
    public Response execute(Starter starter) {
        Map<Currency, List<Rate>> dataSet = dataSetRepository.getDataSet(starter.getCurrencies());
        Map<Currency, List<Rate>> rates = forecaster.forecast(dataSet, starter.getForecastDate(), starter.getAlgorithm());
        return convertorFactory.getConvertor(starter.getOutput()).convert(rates);
    }
}