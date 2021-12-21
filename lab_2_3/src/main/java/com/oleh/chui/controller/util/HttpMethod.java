package com.oleh.chui.controller.util;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public String getValue() {
        return this.name();
    }
}
