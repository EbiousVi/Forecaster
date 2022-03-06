package com.ebious.forecaster.forecast.engine;

import com.ebious.forecaster.forecast.domain.Starter;
import com.ebious.forecaster.forecast.domain.forecast.Forecast;
import com.ebious.forecaster.forecast.convertor.ConvertorFactory;
import com.ebious.forecaster.forecast.domain.dataset.CSVDataSetObject;
import com.ebious.forecaster.forecast.forecast.ForecastPrevSevenExchangeRates;
import com.ebious.forecaster.forecast.forecast.Forecaster;
import com.ebious.forecaster.forecast.suppliers.DataSetSupplier;
import com.ebious.forecaster.forecast.suppliers.csv.CsvDataSetSupplier;

import java.util.List;

public class EngineImpl implements Engine {

    private static EngineImpl engineImpl;
    private final Forecaster<CSVDataSetObject> forecastByAverage = new ForecastPrevSevenExchangeRates();
    private final DataSetSupplier<CSVDataSetObject> dataSetSupplier = new CsvDataSetSupplier();
    private final ConvertorFactory convertorFactory = new ConvertorFactory();

    public static EngineImpl getEngine() {
        if (engineImpl == null) {
            engineImpl = new EngineImpl();
        }
        return engineImpl;
    }

    @Override
    public Object start(Starter starter) {
        List<CSVDataSetObject> dataSet = dataSetSupplier.getDataSet(starter.getCurrency());
        List<Forecast> forecasts = forecastByAverage.doForecast(dataSet, starter);
        return convertorFactory.wrap(forecasts, starter.getOutputType());
    }
}
