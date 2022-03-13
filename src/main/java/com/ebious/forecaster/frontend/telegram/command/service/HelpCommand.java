package com.ebious.forecaster.frontend.telegram.command.service;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends ServiceCommand {

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = (user.getUserName() != null) ? user.getUserName() : String.format("%s %s", user.getLastName(), user.getFirstName());
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Home work command\n" +
                        "rate TRY -date tomorrow -alg linear\n" +
                        "rate TRY -date 09.06.2022 -alg linear\n" +
                        "rate USD -period week -alg linear -output list\n" +
                        "rate USD,TRY -period month -alg linear -output graph");
    }
}
