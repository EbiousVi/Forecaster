package com.ebious.forecaster.model.convertor;

import com.ebious.forecaster.model.domain.enums.Output;
import com.ebious.forecaster.model.exception.UnsupportedOutputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;

public class ConvertorFactory {
    private static final Logger logger = LoggerFactory.getLogger(ConvertorFactory.class);
    private static final Map<Output, Convertor> factory = new EnumMap<>(Output.class);

    static {
        factory.put(Output.DEFAULT, new TextConvertor());
        factory.put(Output.LIST, new TextConvertor());
        factory.put(Output.GRAPH, new GraphConvertor());
    }

    public Convertor getConvertor(Output output) {
        if (!factory.containsKey(output)) {
            String message = String.format("Unsupported output = %s for Forecast!", output);
            logger.error(message);
            throw new UnsupportedOutputException(message);

        }
        return factory.get(output);
    }
}
