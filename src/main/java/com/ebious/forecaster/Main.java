package com.ebious.forecaster;

import com.ebious.forecaster.view.telegram.context.BotContext;

public class Main {

    public static void main(String[] args) {
        BotContext botContext = new BotContext();
        botContext.run();
    }
}
