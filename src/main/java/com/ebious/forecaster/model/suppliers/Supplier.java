package com.ebious.forecaster.model.suppliers;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.exception.DataSetSupplierException;
import com.ebious.forecaster.model.exception.InternalAppError;
import com.ebious.forecaster.model.suppliers.csv.CsvDataSetSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Supplier {

    private static final Logger logger = LoggerFactory.getLogger(Supplier.class);

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
                e.printStackTrace();
                logger.warn("DataSet supplier = {} not available now!", supplier.getDataSet(currencies).getClass().getName(), e);
            }
            assert dataSets != null;
            if (!dataSets.isEmpty()) return dataSets;
        }
        throw new DataSetSupplierException("All data set suppliers not available now! Try again later!");
    }
}
