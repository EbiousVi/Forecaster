package com.ebious.forecaster.model.suppliers.csv;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.exception.CsvParserException;
import com.ebious.forecaster.model.exception.DataSetSupplierException;
import com.ebious.forecaster.model.suppliers.DataSetSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvDataSetSupplier implements DataSetSupplier {

    private static final Logger logger = LoggerFactory.getLogger(DataSetSupplier.class);
    private static final Path CSV_STORAGE = Paths.get("src", "main", "resources", "storage", "data-set", "csv");
    private static final int TITLE_ROW_INDEX = 1;
    private final CsvDataSetLineParser csvDataSetLineParser = new CsvDataSetLineParser();

    public Map<Currency, List<Rate>> deliverDataSet(List<Currency> currencies) {
        Map<Currency, List<Rate>> dataSets = new EnumMap<>(Currency.class);
        for (Currency currency : currencies) {
            dataSets.put(currency, readDataSetFromCsv(findCsvByCurrency(currency)));
        }
        return dataSets;
    }

    private List<Rate> readDataSetFromCsv(Path path) {
        try {
            return new BufferedReader(new FileReader(path.toFile()))
                    .lines()
                    .skip(TITLE_ROW_INDEX)
                    .map(csvDataSetLineParser::parseLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            String message = String.format("File not found = %s", path);
            logger.error(message, e);
            throw new DataSetSupplierException(message, e);
        } catch (CsvParserException e) {
            logger.error(e.getMessage(), e);
            throw new DataSetSupplierException(e.getMessage(), e);
        }
    }

    private Path findCsvByCurrency(Currency currency) {
        try {
            return Files.list(CSV_STORAGE)
                    .filter(path -> isCsvMatchesToCurrency(path, currency))
                    .findFirst()
                    .orElseThrow(() -> new CsvParserException("CSV not found by currency = " + currency));
        } catch (IOException e) {
            String message = "Storage unavailable now!";
            logger.error(message, e);
            throw new CsvParserException(message, e);
        }
    }

    private boolean isCsvMatchesToCurrency(Path path, Currency currency) {
        String csvFilename = path.getFileName().toString().toLowerCase();
        String currencyName = currency.name().toLowerCase();
        return csvFilename.contains(currencyName);
    }
}
