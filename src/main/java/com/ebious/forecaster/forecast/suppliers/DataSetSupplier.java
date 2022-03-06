package com.ebious.forecaster.forecast.suppliers;

import com.ebious.forecaster.forecast.domain.enums.Currency;

import java.util.List;

public interface DataSetSupplier<E> {

    List<E> getDataSet(Currency currency);
}
