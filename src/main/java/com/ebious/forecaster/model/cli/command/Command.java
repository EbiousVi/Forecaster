package com.ebious.forecaster.model.cli.command;

public enum Command {
    RATE("rate "),
    HELP("help");

    public final String value;

    Command(String value) {
        this.value = value;
    }
}
