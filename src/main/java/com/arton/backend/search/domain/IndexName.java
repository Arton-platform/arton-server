package com.arton.backend.search.domain;

public enum IndexName {
    PERFORMANCE("performance*"),
    LOG("logstash*");

    private String value;

    IndexName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
