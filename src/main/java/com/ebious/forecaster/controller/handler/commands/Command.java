package com.ebious.forecaster.controller.handler.commands;

public enum Command {
    RATE("rate"),
    HELP("help");

    public final String value;

    Command(String value) {
        this.value = value;
    }
}
