package com.ebious.forecaster.controller.gateway;

import com.ebious.forecaster.controller.controller.Controller;
import com.ebious.forecaster.controller.handler.RequestHandler;

public class FrontController {

    private static FrontController frontController;
    private final RequestHandler requestHandler = new RequestHandler();

    public static FrontController getFrontController() {
        if (frontController == null) {
            frontController = new FrontController();
        }
        return frontController;
    }

    public Response run(Request request) {
        try {
            Controller controller = requestHandler.process(request);
            return controller.perform(request);
        } catch (Exception e) {
            return new Response(e.getMessage(), ContentType.TEXT);
        }
    }
}
