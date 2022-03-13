package com.ebious.forecaster.controller.controller;

import com.ebious.forecaster.controller.gateway.Request;
import com.ebious.forecaster.controller.gateway.Response;

public interface Controller {

    Response perform(Request request);
}
