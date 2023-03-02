package com.arton.backend.infra.mail;

/**
 * sendMailOnlyText 텍스트만 보내기
 * sendMailWithAttachment 첨부파일과 함께 보내기
 */
public interface EmailUseCase {
    void sendMailOnlyText(MailDto details);
    void sendPasswordMailByHTML(MailDto details);
    void sendMailWithMultipleReceivers(MailMultiReceiversDto details);
    void sendMailWithAttachment(MailDto details);
}
