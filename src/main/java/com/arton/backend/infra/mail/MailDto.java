package com.arton.backend.infra.mail;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MailDto {
    private String receiver;
    private String messageBody;
    private String subject;
    private String attachment;
}
