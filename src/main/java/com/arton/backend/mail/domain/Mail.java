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
    private String code;
    private String description;
    private String url;
    private String title;
    /**
     * 등록일
     */
    private LocalDateTime createdDate;
    /**
     * 업데이트일
     */
    private LocalDateTime updatedDate;

    @Builder
    public Mail(Long id, String code, String description, String url, String title, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.url = url;
        this.title = title;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}