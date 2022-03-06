package com.ebious.forecaster.forecast;

import com.ebious.forecaster.forecast.domain.Starter;
import com.ebious.forecaster.forecast.domain.enums.Currency;
import com.ebious.forecaster.forecast.domain.dataset.CSVDataSetObject;
import com.ebious.forecaster.forecast.domain.forecast.Forecast;
import com.ebious.forecaster.forecast.domain.enums.Interval;
import com.ebious.forecaster.forecast.domain.enums.ConvertorType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestData {

    public static Forecast forecastDay1 = new Forecast(77.25D, LocalDate.parse("2022-02-01"), Currency.USD);
    public static Forecast forecastDay2 = new Forecast(77.10D, LocalDate.parse("2022-02-02"), Currency.USD);
    public static Forecast forecastDay3 = new Forecast(76.20D, LocalDate.parse("2022-02-03"), Currency.USD);
    public static Forecast forecastDay4 = new Forecast(77.50D, LocalDate.parse("2022-02-04"), Currency.USD);
    public static Forecast forecastDay5 = new Forecast(79.10D, LocalDate.parse("2022-02-05"), Currency.USD);
    public static Forecast forecastDay6 = new Forecast(77.90D, LocalDate.parse("2022-02-06"), Currency.USD);
    public static Forecast forecastDay7 = new Forecast(78.20D, LocalDate.parse("2022-02-07"), Currency.USD);

    public static CSVDataSetObject csvDataSetObjectDay1 =
            new CSVDataSetObject("2022-02-01", "77.25", "Доллар США");
    public static CSVDataSetObject csvDataSetObjectDay2 =
            new CSVDataSetObject("2022-02-02", "77.10", "Доллар США");
    public static CSVDataSetObject csvDataSetObjectDay3 =
            new CSVDataSetObject("2022-02-03", "76.20", "Доллар США");
    public static CSVDataSetObject csvDataSetObjectDay4 =
            new CSVDataSetObject("2022-02-04", "77.50", "Доллар США");
    public static CSVDataSetObject csvDataSetObjectDay5 =
            new CSVDataSetObject("2022-02-05", "79.10", "Доллар США");
    public static CSVDataSetObject csvDataSetObjectDay6 =
            new CSVDataSetObject("2022-02-06", "77.90", "Доллар США");
    public static CSVDataSetObject csvDataSetObjectDay7 =
            new CSVDataSetObject("2022-02-07", "78.20", "Доллар США");
    public static Starter starterTomorrow = new Starter(Currency.USD, Interval.TOMORROW, ConvertorType.CONSOLE);
    public static Starter starterWeek = new Starter(Currency.USD, Interval.WEEK, ConvertorType.CONSOLE);

    public static List<Forecast> getTestForecast() {
        return Arrays.asList(forecastDay1, forecastDay2, forecastDay3, forecastDay4, forecastDay5, forecastDay6, forecastDay7);
    }

    public static List<CSVDataSetObject> getTestCSVDataSetObject() {
        List<Forecast> testForecast = getTestForecast();
        return Arrays.asList(csvDataSetObjectDay1, csvDataSetObjectDay2, csvDataSetObjectDay3,
                csvDataSetObjectDay4, csvDataSetObjectDay5, csvDataSetObjectDay6, csvDataSetObjectDay7);
    }
}
