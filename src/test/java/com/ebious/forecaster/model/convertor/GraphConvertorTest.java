package com.ebious.forecaster.model.convertor;

import com.ebious.forecaster.controller.gateway.ContentType;
import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.utils.DateUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

class GraphConvertorTest {

    final Convertor convertor = new GraphConvertor();

    @Test
    void convert() {
        Currency currency = Currency.USD;
        Map<Currency, List<Rate>> dataSet = new HashMap<>();
        LocalDate parse = LocalDate.parse("22.03.2022", DateUtils.PARSE_FORMATTER);
        dataSet.put(currency, Arrays.asList((new Rate(parse, 100D))));

        Response response = convertor.convert(dataSet);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(new File(response.getBody())).exists();
        softAssertions.assertThat(response.getContentType()).isEqualTo(ContentType.FILE);
        softAssertions.assertAll();
    }
}