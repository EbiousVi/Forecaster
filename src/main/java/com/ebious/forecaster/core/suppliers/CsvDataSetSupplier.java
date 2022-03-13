package com.ebious.forecaster.core.suppliers;

import com.ebious.forecaster.core.domain.entity.dataset.DataSet;
import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.core.exception.InternalAppError;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CsvDataSetSupplier implements DataSetSupplier {

    private static final Path CSV_STORAGE = Paths.get("src/main/resources/storage/data-set/csv");
    private static final int TITLE_ROW_INDEX = 1;
    private static final int NOMINAL_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final int CURS_INDEX = 2;
    private static final int CDX_INDEX = 3;

    public Map<Currency, List<? extends DataSet>> getDataSets(List<Currency> currencies) {
        Map<Currency, List<? extends DataSet>> dataSets = new EnumMap<>(Currency.class);
        for (Currency currency : currencies) {
            dataSets.put(currency, readDataSetFromCsv(getCsvDataSetByCurrency(currency)));
        }
        return dataSets;
    }

    List<DataSet> readDataSetFromCsv(Path path) {
        try {
            return new BufferedReader(new FileReader(path.toFile()))
                    .lines()
                    .skip(TITLE_ROW_INDEX)
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new InternalAppError("CSV data set not found!");
        }
    }

    // Как это разделить?Class Parse_CSV_Row, formatter
    private DataSet parseLine(String line) {
        String[] split = line.split(";");
        String nominal = split[NOMINAL_INDEX];
        LocalDate date = LocalDate.parse(split[DATE_INDEX], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        Double curs = Double.parseDouble(split[CURS_INDEX].replaceAll(",", ".").replaceAll("\"", ""));
        String cdx = split[CDX_INDEX];
        return new DataSet(nominal, date, curs, cdx);
    }

    Path getCsvDataSetByCurrency(Currency currency) {
        try {
            Optional<Path> csvPath = Files.list(CSV_STORAGE)
                    .filter(path -> isCsvMatchesToCurrency(path, currency))
                    .findFirst();
            return csvPath.orElseThrow(() -> new InternalAppError("CSV not found by Currency = " + currency));
        } catch (IOException e) {
            throw new InternalAppError("CSV not found by currency = " + currency);
        }
    }

    boolean isCsvMatchesToCurrency(Path path, Currency currency) {
        String csvFilename = path.getFileName().toString().toLowerCase();
        String currencyName = currency.name().toLowerCase();
        return csvFilename.contains(currencyName);
    }
}
