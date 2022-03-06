package com.ebious.forecaster.forecast.suppliers.csv;

import com.ebious.forecaster.forecast.suppliers.DataSetSupplier;
import com.ebious.forecaster.forecast.domain.dataset.CSVDataSetObject;
import com.ebious.forecaster.forecast.domain.enums.Currency;
import com.ebious.forecaster.forecast.exception.InternalAppError;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CsvDataSetSupplier implements DataSetSupplier<CSVDataSetObject> {

    private static final Path csvStorage = Paths.get("src/main/resources/storage/data-set/csv");

    @Override
    public List<CSVDataSetObject> getDataSet(Currency currency) {
        return readDataSetFromCsv(getCsvDataSetByCurrency(currency));
    }

    List<CSVDataSetObject> readDataSetFromCsv(Path path) {
        try {
            return new BufferedReader(new FileReader(path.toFile()))
                    .lines()
                    .skip(1)
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new InternalAppError("CSV data set not found!");
        }
    }

    CSVDataSetObject parseLine(String line) {
        String[] split = line.split(";");
        String date = split[0];
        String curs = split[1].replaceAll(",", ".");
        String cdx = split[2];
        return new CSVDataSetObject(date, curs, cdx);
    }

    Path getCsvDataSetByCurrency(Currency currency) {
        try {
            Optional<Path> csvPath = Files.list(csvStorage)
                    .filter(path -> isCsvMatchesToCurrency(path, currency))
                    .findFirst();
            if (csvPath.isPresent()) {
                return csvPath.get();
            } else {
                throw new InternalAppError("CSV not found by currency = " + currency);
            }
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
