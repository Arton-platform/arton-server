package com.arton.backend.terms.domain;

public enum TermsCase {
    REQUIRED("필수"),
    OPTION("선택");

    private String value;

    TermsCase(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
