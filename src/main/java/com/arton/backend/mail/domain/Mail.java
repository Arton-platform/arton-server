package com.arton.backend.mail.domain;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 자동 메일 도메인
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Mail {
    private Long id;
    /** 분류명 */
    private MailCode mailCode;
    /** 메일 내용 */
    private String content;
    /** 추후 구현시 s3 저장 url */
    private String url;
    /** 제목 */
    private String subject;
    /**
     * 등록일
     */
    private LocalDateTime createdDate;
    /**
     * 업데이트일
     */
    private LocalDateTime updatedDate;

    @Builder
    public Mail(Long id, MailCode mailCode, String content, String url, String subject, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.mailCode = mailCode;
        this.content = content;
        this.url = url;
        this.subject = subject;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}