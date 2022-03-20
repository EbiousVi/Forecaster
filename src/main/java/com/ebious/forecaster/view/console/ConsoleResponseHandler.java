package com.ebious.forecaster.view.console;

import com.ebious.forecaster.controller.gateway.ContentType;
import com.ebious.forecaster.controller.gateway.Response;

public class ConsoleResponseHandler {
    public Response handleResponse(Response response) {
        if (!response.getContentType().equals(ContentType.TEXT)) {
            throw new ConsoleResponseHandlerException("Console output support only text");
        }
        return response;
    }
}
