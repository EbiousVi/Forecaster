package com.ebious.forecaster.core.suppliers;


import com.ebious.forecaster.core.domain.entity.dataset.DataSet;
import com.ebious.forecaster.core.domain.enums.Currency;

import java.util.List;
import java.util.Map;

public interface DataSetSupplier {

    Map<Currency, List<? extends DataSet>> getDataSets(List<Currency> currencies);
}
