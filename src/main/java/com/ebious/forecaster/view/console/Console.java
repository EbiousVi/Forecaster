package com.ebious.forecaster.view.console;

import com.ebious.forecaster.controller.gateway.ContentType;
import com.ebious.forecaster.controller.gateway.FrontController;
import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.controller.gateway.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final FrontController frontController = new FrontController();
    private final ConsoleResponseHandler responseHandler = new ConsoleResponseHandler();

    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            logger.info("Run console");
            while (true) {
                try {
                    String commandLine = bufferedReader.readLine();
                    Response response = frontController.accept(new Request(commandLine));
                    printResponse(responseHandler.handleResponse(response));
                } catch (Exception e) {
                    printResponse(new Response(e.getMessage(), ContentType.TEXT));
                }
            }
        } catch (IOException e) {
            logger.error("How does it possible?", e);
            printResponse(new Response(e.getMessage(), ContentType.TEXT));
        }
    }

    private void printResponse(Response response) {
        System.out.println(response.getBody());
    }
}
