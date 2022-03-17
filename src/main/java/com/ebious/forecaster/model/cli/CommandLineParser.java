package com.ebious.forecaster.model.cli;

public interface CommandLineParser<T> {
    T parse(String commandLine);
}
