package com.ebious.forecaster.console.front;

import com.ebious.forecaster.console.controller.Controller;
import com.ebious.forecaster.console.commands.CommandInfo;
import com.ebious.forecaster.console.handler.ConsoleRequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleFrontController {

    private final ConsoleRequestHandler consoleRequestHandler;

    public ConsoleFrontController(ConsoleRequestHandler consoleRequestHandler) {
        this.consoleRequestHandler = consoleRequestHandler;
    }

    public void init() {
        sendResponse("List of commands \n" + CommandInfo.getCommandTemplateString());
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    String request = bufferedReader.readLine().trim();
                    Controller controller = consoleRequestHandler.process(request);
                    sendResponse(controller.perform(request));
                } catch (Exception e) {
                    sendErrorResponse(e.getMessage());
                }
            }
        } catch (IOException e) {
            sendInputErrResponse(e.getMessage());
        }
    }

    private void sendErrorResponse(Object obj) {
        System.out.println("Error = " + obj);
    }

    private void sendInputErrResponse(Object obj) {
        System.out.println("Incorrect input! " + obj);
    }

    private void sendResponse(String response) {
        System.out.println(response);
    }
}
