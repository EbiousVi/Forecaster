package com.ebious.forecaster.view.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleContext {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleContext.class);

    public void init() {
        logger.info("Init console application");
        Console console = new Console();
        console.run();
    }
}
