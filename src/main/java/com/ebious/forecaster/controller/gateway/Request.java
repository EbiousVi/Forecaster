package com.ebious.forecaster.controller.gateway;

public class Request {

    private final String body;

    public Request(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "body='" + body + '\'' +
                '}';
    }
}
