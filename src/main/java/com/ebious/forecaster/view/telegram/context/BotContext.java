package com.ebious.forecaster.view.telegram.context;

import com.ebious.forecaster.view.telegram.bot.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotContext {

    private static final Logger logger = LoggerFactory.getLogger(BotContext.class);

    public void run() {
        try {
            logger.info("Run application");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
            logger.info("Initialize successfully");
        } catch (TelegramApiException e) {
            logger.error("Initialize failed!", e);
        }
    }
}
