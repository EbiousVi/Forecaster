package com.ebious.forecaster.view.telegram.bot;

import com.ebious.forecaster.controller.gateway.FrontController;
import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.view.telegram.command.HelpCommand;
import com.ebious.forecaster.view.telegram.command.StartCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Map;

public class Bot extends TelegramLongPollingCommandBot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private static final Map<String, String> env = System.getenv();
    private final String BOT_USERNAME = env.get("BOT_USERNAME");
    private final String BOT_TOKEN = env.get("BOT_TOKEN");
    private final FrontController frontController = new FrontController();

    public Bot() {
        register(new StartCommand("start", "start"));
        register(new HelpCommand("help", "help"));
        logger.info("init Bot");
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        Response response = frontController.accept(new Request(msg.getText()));
        resolveResponseExecutor(response, chatId, userName);
    }

    private void resolveResponseExecutor(Response response, Long chatId, String userName) {
        switch (response.getContentType()) {
            case FILE:
                sendFile(chatId, userName, response.getBody());
                break;
            case TEXT:
                sendText(chatId, userName, response.getBody());
                break;
            default:
                sendText(chatId, userName, "Sorry, I didn't understand you! Try help!");
        }
    }

    private void sendText(Long chatId, String userName, String text) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId.toString());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Telegram API execute exception", e);
        }
    }

    private void sendFile(Long chatId, String userName, String path) {
        SendDocument sendDocument = new SendDocument();
        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File(path));
        sendDocument.setDocument(inputFile);
        sendDocument.setChatId(chatId.toString());
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            logger.error("Telegram API execute exception", e);
        }
    }

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }
}