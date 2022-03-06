package com.ebious.forecaster.console.handler;

import com.ebious.forecaster.console.commands.CommandInfo;
import com.ebious.forecaster.console.controller.Controller;
import com.ebious.forecaster.console.controller.ExitController;
import com.ebious.forecaster.console.controller.HelpController;
import com.ebious.forecaster.console.exception.ConsoleRequestException;
import com.ebious.forecaster.console.commands.Command;
import com.ebious.forecaster.console.controller.RateController;

import java.util.EnumMap;
import java.util.Map;

public class ConsoleRequestHandler {

    private final Map<Command, Controller> mapping = new EnumMap<>(Command.class);

    {
        mapping.put(Command.RATE, new RateController());
        mapping.put(Command.HELP, new HelpController());
        mapping.put(Command.EXIT, new ExitController());
    }

    public Controller process(String request) throws ConsoleRequestException {
        for (Command command : mapping.keySet()) {
            boolean matches = request.matches(command.value);
            if (matches) return mapping.get(command);
        }
        throw new ConsoleRequestException("Command does not match any pattern! \n" + CommandInfo.getCommandTemplateString());
    }
}
