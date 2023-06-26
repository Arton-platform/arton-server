package com.arton.backend.review.domain;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {
    private long id;
    private long performanceId;
    private long userId;
    private String content;
    private float starScore;
    private Long hit;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updatedDate;

    @Builder
    public Review(long id, long performanceId, long userId, String content, float starScore, Long hit, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.performanceId = performanceId;
        this.userId = userId;
        this.content = content;
        this.starScore = starScore;
        this.hit = hit;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
