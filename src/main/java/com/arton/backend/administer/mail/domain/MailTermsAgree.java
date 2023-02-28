package com.arton.backend.administer.mail.domain;

public enum MailTermsAgree {
    회원전체(null), 수신거부제외("Y");

    private String value;

    MailTermsAgree(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
