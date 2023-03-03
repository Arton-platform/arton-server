package com.arton.backend.mail.application.data;

import com.arton.backend.mail.domain.Mail;
import com.arton.backend.mail.domain.MailCode;
import lombok.*;

@Data
public class MailTemplateRequestDto {
    /** 분류명 */
    private MailCode mailCode;
    /** 메일 내용 */
    private String content;
    /** 추후 구현시 s3 저장 url */
    private String url;
    /** 제목 */
    private String subject;

    @Builder
    public MailTemplateRequestDto(MailCode mailCode, String content, String url, String subject) {
        this.mailCode = mailCode;
        this.content = content;
        this.url = url;
        this.subject = subject;
    }

    public static Mail toDomainFromDto(MailTemplateRequestDto requestDto) {
        return Mail.builder()
                .url(requestDto.getUrl())
                .content(requestDto.getContent())
                .mailCode(requestDto.getMailCode())
                .subject(requestDto.getSubject())
                .build();
    }
}
