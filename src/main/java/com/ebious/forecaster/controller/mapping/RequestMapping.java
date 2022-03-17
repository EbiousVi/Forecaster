package com.ebious.forecaster.controller.mapping;

import com.ebious.forecaster.controller.controller.Controller;
import com.ebious.forecaster.controller.controller.HelpController;
import com.ebious.forecaster.controller.exception.RequestMappingException;
import com.ebious.forecaster.controller.mapping.commands.Command;
import com.ebious.forecaster.controller.controller.RateController;
import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.model.cli.domain.Info;

import java.util.EnumMap;
import java.util.Map;

public final class RequestMapping {

    private final Map<Command, Controller> commands = new EnumMap<>(Command.class);

    {
        commands.put(Command.RATE, new RateController());
        commands.put(Command.HELP, new HelpController());
    }

    public Controller process(Request request) {
        for (Command command : commands.keySet()) {
            boolean matches = request.getBody().startsWith(command.value);
            if (matches) return commands.get(command);
        }
        String msg = "Illegal command!\n" + Info.LIST_OF_COMMAND + Info.INFO_COMMAND + Info.RATE_COMMAND;
        throw new RequestMappingException(msg);
    }
}
