package com.arton.backend.mail.application.port.in;

import com.arton.backend.mail.application.data.MailDto;
import com.arton.backend.mail.application.data.MailMultiReceiversDto;

/**
 * sendMailOnlyText 텍스트만 보내기
 * sendMailWithAttachment 첨부파일과 함께 보내기
 */
public interface EmailUseCase {
    void sendMailOnlyText(MailDto details);
    void sendMailByHTML(MailDto details);
    void sendPasswordMailByHTML(MailDto details);
    void sendMailWithMultipleReceivers(MailMultiReceiversDto details);
    void sendMailWithAttachment(MailDto details);
}
