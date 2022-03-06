package com.ebious.forecaster.console.controller;

import com.ebious.forecaster.console.commands.CommandInfo;

public class HelpController implements Controller {

    @Override
    public String perform(String request) {
        return "Available command list \n" + CommandInfo.getCommandTemplateString();
    }
}
