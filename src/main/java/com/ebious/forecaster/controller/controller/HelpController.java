package com.ebious.forecaster.controller.controller;

import com.ebious.forecaster.controller.gateway.ContentType;
import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.controller.gateway.Response;

public class HelpController implements Controller {

    @Override
    public Response perform(Request request) {
        return new Response("nothing help ha-ha-ha", ContentType.TEXT);
    }
}
