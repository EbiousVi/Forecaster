package com.ebious.forecaster.controller.gateway;

import com.ebious.forecaster.controller.controller.Controller;
import com.ebious.forecaster.controller.mapping.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontController {

    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);
    private final RequestMapping requestMapping = new RequestMapping();

    public Response accept(Request request) {
        try {
            Controller controller = requestMapping.process(request);
            return controller.perform(request);
        } catch (Exception e) {
            logger.warn("FrontController intercept exception!", e);
            return new Response(e.getMessage(), ContentType.TEXT);
        }
    }
}
