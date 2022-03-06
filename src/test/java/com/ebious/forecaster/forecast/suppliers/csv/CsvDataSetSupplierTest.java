package com.ebious.forecaster.forecast.suppliers.csv;

import com.ebious.forecaster.forecast.domain.enums.Currency;
import com.ebious.forecaster.forecast.domain.dataset.CSVDataSetObject;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvDataSetSupplierTest {

    CsvDataSetSupplier csvDataSetSupplier = new CsvDataSetSupplier();

    @Test
    void getDataSetUSD() {
        List<CSVDataSetObject> dataSet = csvDataSetSupplier.getDataSet(Currency.USD);
        assertThat(dataSet)
                .hasSize(4956)
                .anyMatch(item -> item.getCdx().equals(Currency.USD.value));
    }

    @Test
    void getDataSetEUR() {
        List<CSVDataSetObject> dataSet = csvDataSetSupplier.getDataSet(Currency.EUR);
        assertThat(dataSet)
                .hasSize(4956)
                .anyMatch(item -> item.getCdx().equals(Currency.EUR.value));
    }


    @Test
    void readDataSetFromCsv() {
        Path testCsv = Paths.get("src/test/resources/storage/data-set/csv/EUR_F01_02_2002_T01_02_2022.csv");
        List<CSVDataSetObject> dateSet = csvDataSetSupplier.readDataSetFromCsv(testCsv);
        assertThat(dateSet)
                .hasSize(4956)
                .anyMatch(item -> item.getCdx().equals(Currency.EUR.value));
    }

    @Test
    void parseLine() {
        String csvString = "01.02.2022;86,5032;Евро";
        CSVDataSetObject expected = new CSVDataSetObject("01.02.2022", "86.5032", "Евро");
        CSVDataSetObject csvDataSetObject = csvDataSetSupplier.parseLine(csvString);
        assertThat(csvDataSetObject).isEqualTo(expected);
    }

    @Test
    void getCsvDataSetByCurrency() {
        Path path = csvDataSetSupplier.getCsvDataSetByCurrency(Currency.USD);
        assertThat(path).exists();
    }

    @Test
    void isCsvMatchesToCurrency() {
        Path testCsv = Paths.get("src/test/resources/storage/data-set/csv/EUR_F01_02_2002_T01_02_2022.csv");
        boolean csvMatchesToCurrency = csvDataSetSupplier.isCsvMatchesToCurrency(testCsv, Currency.EUR);
        assertThat(csvMatchesToCurrency).isTrue();
    }
}