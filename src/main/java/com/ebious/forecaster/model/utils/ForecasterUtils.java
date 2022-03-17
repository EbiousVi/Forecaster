package com.ebious.forecaster.model.utils;

import com.ebious.forecaster.model.domain.entity.Rate;

import java.util.Collections;
import java.util.List;

public class ForecasterUtils {

    public static List<Rate> getLastRateAsList(List<Rate> rates) {
        return Collections.singletonList(rates.get(rates.size() - 1));
    }

}
