package com.ebious.forecaster.model.suppliers.csv;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.exception.CsvParserException;
import com.ebious.forecaster.model.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CsvDataSetLineParser {

    private static final String CSV_LINE_SEPARATOR = ";";
    private static final String LOCAL_DOUBLE_SEPARATOR = ",";
    private static final String ISO_DOUBLE_SEPARATOR = ".";
    private static final String EXTRA_QUOTES = "\"";
    private final static int DATE_INDEX = 1;
    private final static int CURS_INDEX = 2;

    public Rate parseLine(String line) {
        String[] tokens = line.split(CSV_LINE_SEPARATOR);
        LocalDate date = parseDate(tokens[DATE_INDEX]);
        Double curs = parseCurs(tokens[CURS_INDEX]);
        return new Rate(date, curs);
    }

    private LocalDate parseDate(String token) {
        try {
            return LocalDate.parse(token, DateUtils.PARSE_FORMATTER);
        } catch (DateTimeParseException e) {
            String errMsg = String.format("invalid date = %s at CSV", token);
            throw new CsvParserException(errMsg, e);
        }
    }

    private double parseCurs(String token) {
        String cursValue = token.replaceAll(LOCAL_DOUBLE_SEPARATOR, ISO_DOUBLE_SEPARATOR).replaceAll(EXTRA_QUOTES, "");
        try {
            return Double.parseDouble(cursValue);
        } catch (NumberFormatException e) {
            String errMsg = String.format("invalid curs = %s at CSV", token);
            throw new CsvParserException(errMsg);
        }
    }
}
