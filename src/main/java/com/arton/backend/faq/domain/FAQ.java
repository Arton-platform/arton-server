package com.arton.backend.faq.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class FAQ {
    // VO와 같이 데이터를 정의하는부분
    private long faqId;
    private String title;
    private String content;
    private long userId;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updatedDate;

    @Builder
    public FAQ(long faqId, String title, String content, long userId, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.faqId = faqId;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
