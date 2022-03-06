package com.ebious.forecaster.console.commands;

/**
 * Шаблоны для входящих команд
 */
public enum Command {
    RATE("^rate\\s[A-Z]{3}\\s[a-z]+$"),
    HELP("^help$"),
    EXIT("^exit$");

    public final String value;

    Command(String value) {
        this.value = value;
    }
}
