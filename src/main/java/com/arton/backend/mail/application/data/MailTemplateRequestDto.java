package com.arton.backend.mail.application.data;

import com.arton.backend.mail.domain.Mail;
import lombok.*;

@Data
public class MailTemplateRequestDto {
    /** 분류명 */
    private String code;
    /** 메일 내용 */
    private String description;
    /** 추후 구현시 s3 저장 url */
    private String url;
    /** 제목 */
    private String subject;

    @Builder
    public MailTemplateRequestDto(String code, String description, String url, String subject) {
        this.code = code;
        this.description = description;
        this.url = url;
        this.subject = subject;
    }

    public static Mail toDomainFromDto(MailTemplateRequestDto requestDto) {
        return Mail.builder()
                .url(requestDto.getUrl())
                .description(requestDto.getDescription())
                .code(requestDto.getCode())
                .subject(requestDto.getSubject())
                .build();
    }
}