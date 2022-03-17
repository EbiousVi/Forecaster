package com.ebious.forecaster.model.convertor;

import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.controller.gateway.ContentType;
import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.model.domain.enums.GraphColor;
import com.ebious.forecaster.model.exception.ConvertorException;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonConfig;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GraphConvertor implements Convertor {
    private static final Logger logger = LoggerFactory.getLogger(ConvertorFactory.class);
    private static final Path STORAGE = Paths.get("src/main/resources/storage/plt-fig");
    private static final String PYTHON_CONFIG_PATH = "/home/v/env-3.8/bin/python";
    private static final String LINE_FORMAT = "o";
    private static final String LINE_STYLE = "--";
    private static final String PLOT_TITLE = "Exchange rates";
    private static final String X_LABEL = "interval";
    private static final String Y_LABEL = "value";
    private static final DateTimeFormatter FIG_FILENAME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
    private static final String FIG_EXTENSION = ".png";

    @Override
    public Response convert(Map<Currency, List<Rate>> rates) {
        try {
            String drawGraphPath = drawGraph(rates);
            return new Response(drawGraphPath, ContentType.FILE);
        } catch (PythonExecutionException | IOException e) {
            logger.error(e.getMessage(), e);
            throw new ConvertorException(e.getMessage(), e);
        }
    }

    public String drawGraph(Map<Currency, List<Rate>> rates) throws PythonExecutionException, IOException {
        Plot plt = Plot.create(PythonConfig.pythonBinPathConfig(PYTHON_CONFIG_PATH));
        plt.title(PLOT_TITLE);
        plt.xlabel(X_LABEL);
        plt.ylabel(Y_LABEL);
        for (Map.Entry<Currency, List<Rate>> pair : rates.entrySet()) {
            List<Double> rateValues = getRateValues(pair.getValue());
            setPlot(plt, pair.getKey(), rateValues);
        }
        plt.legend();
        String figPath = getPathToSaveFig();
        plt.savefig(getPathToSaveFig());
        plt.executeSilently();
        return figPath;
    }

    private void setPlot(Plot plt, Currency currency, List<Double> cursValues) {
        plt.plot()
                .add(getDaysRange(cursValues.size()), cursValues, LINE_FORMAT)
                .label(currency.name())
                .linestyle(LINE_STYLE)
                .color(GraphColor.getColor(currency));
    }

    private List<Double> getRateValues(List<Rate> rates) {
        return rates.stream()
                .map(Rate::getCurs)
                .collect(Collectors.toList());
    }

    private List<Double> getDaysRange(Integer period) {
        List<Double> days = new ArrayList<>();
        for (double day = 1; day <= period; day++) {
            days.add(day);
        }
        return days;
    }

    private String getPathToSaveFig() {
        return STORAGE.toAbsolutePath().resolve(generateUniquePlotFigFilename()).toString();
    }

    private String generateUniquePlotFigFilename() {
        return FIG_FILENAME_FORMAT.format(LocalDateTime.now()) + FIG_EXTENSION;
    }
}