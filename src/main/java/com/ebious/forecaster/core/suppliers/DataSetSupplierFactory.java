package com.ebious.forecaster.core.suppliers;

import java.util.ArrayList;
import java.util.List;

public final class DataSetSupplierFactory {

    private final List<DataSetSupplier> availableDataSetSuppliers = new ArrayList<>();
    private final DataSetSupplier defaultDataSetSupplier = new CsvDataSetSupplier();
    //private 
    public DataSetSupplierFactory() {
    }

    {
        availableDataSetSuppliers.add(new CsvDataSetSupplier());
    }

    public DataSetSupplier getDefaultDataSetSupplier() {
        return defaultDataSetSupplier;
    }
}
