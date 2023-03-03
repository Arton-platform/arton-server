package com.arton.backend.mail.domain;

/**
 * 메일 템플릿을 위한 코드
 */
public enum MailCode {
    REGISTER("회원가입시"), WITHDRAW("회원탈퇴시");

    private String value;

    MailCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
