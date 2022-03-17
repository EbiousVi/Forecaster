package com.ebious.forecaster.model.suppliers;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.exception.DataSetSupplierException;
import com.ebious.forecaster.model.suppliers.csv.CsvDataSetSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class DataSetProducer {

    private static final Logger logger = LoggerFactory.getLogger(DataSetProducer.class);

    /**
     * Priority data set supplier factory. Integer is priority.
     */
    private final Map<Integer, DataSetSupplier> factory = new LinkedHashMap<>();

    {
        factory.put(0, new CsvDataSetSupplier());
    }

    public Map<Currency, List<Rate>> getDataSet(List<Currency> currencies) {
        for (DataSetSupplier supplier : factory.values()) {
            Map<Currency, List<Rate>> dataSets = null;
            try {
                dataSets = supplier.getDataSet(currencies);
            } catch (Exception e) {
                logger.warn("DataSet supplier = {} not available now!", supplier.getDataSet(currencies).getClass().getName(), e);
            }
            assert dataSets != null;
            if (!dataSets.isEmpty()) return dataSets;
        }
        //В то же время это исключение будет поймано во Фронтконтроллере и повторно залогировано???
        String message = "All data set suppliers not available now! Try again later!";
        logger.error(message);
        throw new DataSetSupplierException(message);
    }
}
