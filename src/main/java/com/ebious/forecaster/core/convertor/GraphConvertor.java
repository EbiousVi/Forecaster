package com.ebious.forecaster.core.convertor;

import com.ebious.forecaster.core.domain.enums.Currency;
import com.ebious.forecaster.core.domain.entity.forecast.Forecast;
import com.ebious.forecaster.controller.gateway.ContentType;
import com.ebious.forecaster.controller.gateway.Response;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonConfig;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GraphConvertor implements Convertor {

    private static final Path storage = Paths.get("src/main/resources/storage/plt-fig");

    @Override
    public Response wrapToResponse(Map<Currency, List<Forecast>> map) {
        try {

            String drawGraphPath = drawGraph(map);
            return new Response(drawGraphPath, ContentType.FILE);
        } catch (PythonExecutionException | IOException e) {
            throw new RuntimeException("can't drow graph");
        }
    }

    public String drawGraph(Map<Currency, List<Forecast>> forecasts) throws PythonExecutionException, IOException {
        Plot plt = Plot.create(PythonConfig.pythonBinPathConfig("/home/v/env-3.8/bin/python"));
        plt.title("Exchange rates");
        plt.xlabel("date");
        plt.ylabel("value");
        for (Map.Entry<Currency, List<Forecast>> pair : forecasts.entrySet()) {
            List<Double> curs = pair.getValue().stream().map(Forecast::getValue).collect(Collectors.toList());
            plt.plot().add(getDaysRange(curs.size()), curs, "o").label(pair.getKey().name()).linestyle("--").color(Colors.getColor());
        }
        plt.legend();
        String pathToSaveImage = getPathToSaveImage();
        plt.savefig(getPathToSaveImage());
        plt.executeSilently();
        return pathToSaveImage;
    }

    private List<Double> getDaysRange(Integer integer) {
        List<Double> doubles = new ArrayList<>();
        for (int i = 1; i <= integer; i++) {
            doubles.add((double) i);
        }
        return doubles;
    }

    private String getPathToSaveImage() {
        return storage.toAbsolutePath().resolve(generateUniquePlotImageFilename()).toString();
    }

    private String generateUniquePlotImageFilename() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        return dtf.format(LocalDateTime.now()) + ".png";
    }

    public static class Colors {
        private final static String RED = "red";
        private final static String ORANGE = "orange";
        private final static String YELLOW = "yellow";
        private final static String GREEN = "green";
        private final static String BLUE = "blue";
        private final static String DEFAULT_COLOR = "black";
        private final static List<String> colors = Arrays.asList(RED, ORANGE, YELLOW, GREEN, BLUE);

        public static String getColor() {
            return colors.get(new Random().nextInt(colors.size()));
        }
    }
}