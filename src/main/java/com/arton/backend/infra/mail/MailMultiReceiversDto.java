package com.arton.backend.infra.mail;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class MailMultiReceiversDto {
    private List<String> receivers;
    private String messageBody;
    private String subject;
    private String attachment;

    @Builder
    public MailMultiReceiversDto(List<String> receivers, String messageBody, String subject, String attachment) {
        this.receivers = receivers;
        this.messageBody = messageBody;
        this.subject = subject;
        this.attachment = attachment;
    }
}
