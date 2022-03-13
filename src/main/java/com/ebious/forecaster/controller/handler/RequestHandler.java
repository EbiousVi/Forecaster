package com.ebious.forecaster.controller.handler;

import com.ebious.forecaster.controller.controller.Controller;
import com.ebious.forecaster.controller.controller.HelpController;
import com.ebious.forecaster.controller.exception.RequestHandlerException;
import com.ebious.forecaster.controller.handler.commands.Command;
import com.ebious.forecaster.controller.controller.RateController;
import com.ebious.forecaster.controller.gateway.Request;

import java.util.EnumMap;
import java.util.Map;

public class RequestHandler {

    private final Map<Command, Controller> mapping = new EnumMap<>(Command.class);

    {
        mapping.put(Command.RATE, new RateController());
        mapping.put(Command.HELP, new HelpController());
    }

    public Controller process(Request request) {
        for (Command command : mapping.keySet()) {
            boolean matches = request.getBody().startsWith(command.value);
            if (matches) return mapping.get(command);
        }
        throw new RequestHandlerException("Illegal command! Try help! But help doesn't work yet :)");
    }
}
