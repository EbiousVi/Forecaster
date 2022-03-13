package com.ebious.forecaster.controller.gateway;

public class Response {
    private final String body;
    private final ContentType contentType;

    public Response(String body, ContentType contentType) {
        this.body = body;
        this.contentType = contentType;
    }

    public String getBody() {
        return body;
    }

    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "Response{" +
                "body='" + body + '\'' +
                ", contentType=" + contentType +
                '}';
    }
}
