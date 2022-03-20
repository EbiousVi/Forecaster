package com.ebious.forecaster.model.repository;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.exception.DataSetSupplierException;
import com.ebious.forecaster.model.suppliers.DataSetSupplier;
import com.ebious.forecaster.model.suppliers.csv.CsvDataSetSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public final class DataSetRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataSetRepository.class);
    private final List<DataSetSupplier> suppliers = new ArrayList<>();

    {
        suppliers.add(new CsvDataSetSupplier());
    }

    public Map<Currency, List<Rate>> getDataSet(List<Currency> currencies) {
        for (DataSetSupplier supplier : suppliers) {
            try {
                Map<Currency, List<Rate>> dataSet = supplier.deliverDataSet(currencies);
                if (dataSet != null && !dataSet.values().isEmpty())
                    return dataSet;
            } catch (Exception e) {
                logger.info("Data set Supplier not available now!", e);
            }
        }
        String message = "All data set Suppliers not available now! Try again later!";
        logger.error(message);
        throw new DataSetSupplierException(message);
    }
}
