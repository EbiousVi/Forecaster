package com.ebious.forecaster.controller.gateway;

import com.ebious.forecaster.controller.controller.Controller;
import com.ebious.forecaster.controller.mapping.RequestMapping;

public class FrontController {

    private final RequestMapping requestMapping = new RequestMapping();

    public Response accept(Request request) {
        try {
            Controller controller = requestMapping.mapping(request);
            return controller.perform(request);
        } catch (Exception e) {
            return new Response(e.getMessage(), ContentType.TEXT);
        }
    }
}
