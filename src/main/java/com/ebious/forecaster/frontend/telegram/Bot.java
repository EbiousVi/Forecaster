package com.ebious.forecaster.frontend.telegram;

import com.ebious.forecaster.controller.gateway.FrontController;
import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.controller.gateway.Response;
import com.ebious.forecaster.frontend.telegram.command.service.HelpCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class Bot extends TelegramLongPollingCommandBot {

    private final FrontController frontController = FrontController.getFrontController();
    private final String BOT_USERNAME;
    private final String BOT_TOKEN;

    public Bot(String BOT_USERNAME, String BOT_TOKEN) {
        super();
        this.BOT_USERNAME = BOT_USERNAME;
        this.BOT_TOKEN = BOT_TOKEN;
        register(new HelpCommand("help", "help"));
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
        Response response = frontController.run(new Request(msg.getText()));
        resolveResponseExecutor(response, chatId, userName);
    }

    private void resolveResponseExecutor(Response response, Long chatId, String userName) {
        switch (response.getContentType()) {
            case FILE:
                sendFile(chatId, userName, response.getBody());
                break;
            case TEXT:
                sendTexT(chatId, userName, response.getBody());
                break;
            default:
                sendTexT(chatId, userName, "Sorry, I didn't understand you! Try help!");
        }
    }

    private void sendTexT(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendFile(Long chatId, String userName, String path) {
        SendDocument answer = new SendDocument();
        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File(path));
        answer.setDocument(inputFile);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }
}