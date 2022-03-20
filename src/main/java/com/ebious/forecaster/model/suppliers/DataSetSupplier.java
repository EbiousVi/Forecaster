package com.ebious.forecaster.model.suppliers;


import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;

import java.util.List;
import java.util.Map;

public interface DataSetSupplier {

    Map<Currency, List<Rate>> deliverDataSet(List<Currency> currencies);
}
