package com.ebious.forecaster.core.engine;

import com.ebious.forecaster.core.domain.entity.dataset.DataSet;
import com.ebious.forecaster.core.domain.entity.forecast.Forecast;
import com.ebious.forecaster.core.domain.entity.starter.Starter;
import com.ebious.forecaster.core.convertor.ConvertorFactory;
import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.core.forecast.Forecaster;
import com.ebious.forecaster.core.forecast.ForecasterFactory;
import com.ebious.forecaster.core.suppliers.DataSetSupplier;
import com.ebious.forecaster.core.suppliers.DataSetSupplierFactory;
import com.ebious.forecaster.controller.gateway.Response;

import java.util.List;
import java.util.Map;


public class EngineImpl implements Engine {

    private static EngineImpl engineImpl;
    private final ConvertorFactory convertorFactory = new ConvertorFactory();
    private final DataSetSupplierFactory dataSetSupplierFactory = new DataSetSupplierFactory();
    private final ForecasterFactory forecasterFactory = new ForecasterFactory();

    private EngineImpl() {
    }

    public static EngineImpl getEngine() {
        if (engineImpl == null) {
            engineImpl = new EngineImpl();
        }
        return engineImpl;
    }
    //Синглтоны
    @Override
    public Response start(Starter starter) {
        DataSetSupplier dataSetSupplier = dataSetSupplierFactory.getDefaultDataSetSupplier();
        Map<Currency, List<? extends DataSet>> dataSets = dataSetSupplier.getDataSets(starter.getCurrencies());
        Forecaster forecaster = forecasterFactory.getForecaster(starter.getAlgoType());
        //starter убрать, код должен быть как текст.//Rate
        Map<Currency, List<Forecast>> currencyListMap = forecaster.doForecast(dataSets, starter);
        return convertorFactory.convert(currencyListMap, starter.getOutputType());
    }
}