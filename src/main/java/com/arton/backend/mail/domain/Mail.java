package com.arton.backend.mail.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    /**
     * 등록일
     */
    private LocalDateTime createdDate;
    /**
     * 업데이트일
     */
    private LocalDateTime updatedDate;
}