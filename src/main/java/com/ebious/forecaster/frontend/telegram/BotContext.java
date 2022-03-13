package com.ebious.forecaster.frontend.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

public class BotContext {

    private static final Map<String, String> getenv = System.getenv();

    public void run() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(getenv.get("BOT_USERNAME"), getenv.get("BOT_TOKEN")));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
