package com.ebious.forecaster.view.telegram.command;

import com.ebious.forecaster.model.cli.domain.Info;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends ServiceCommand {

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = getUserName(user);
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, Info.LIST_OF_COMMAND + Info.INFO_COMMAND + Info.RATE_COMMAND);
    }
}
