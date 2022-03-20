package com.ebious.forecaster.view.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends ServiceCommand {

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = getUserName(user);
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Welcome to CRINGE!\nI'm forecaster! My predictions allow you to lose the last pennies, Buddy!");
    }
}
