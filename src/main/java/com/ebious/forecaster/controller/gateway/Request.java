package com.ebious.forecaster.controller.gateway;

public class Request {
    private String body;
    //command здесь
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
