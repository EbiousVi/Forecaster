package com.ebious.forecaster.console.controller;

public class ExitController implements Controller {

    @Override
    public String perform(String request) {
        System.exit(1);
        return "Exit";
    }
}
