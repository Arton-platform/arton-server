package com.arton.backend.infra.mail;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MailMultiReceiversDto {
    private List<String> receivers;
    private String messageBody;
    private String subject;
    private String attachment;
}
