package com.ebious.forecaster.console.context;

import com.ebious.forecaster.console.front.ConsoleFrontController;
import com.ebious.forecaster.console.handler.ConsoleRequestHandler;

public class ConsoleApplicationContext {

    public void run() {
        ConsoleFrontController consoleFrontController = new ConsoleFrontController(new ConsoleRequestHandler());
        consoleFrontController.init();
    }
}
