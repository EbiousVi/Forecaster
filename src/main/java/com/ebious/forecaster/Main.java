package com.ebious.forecaster;

import com.ebious.forecaster.frontend.telegram.BotContext;

public class Main {

    public static void main(String[] args) {
        BotContext botContext = new BotContext();
        botContext.run();
    }
}
