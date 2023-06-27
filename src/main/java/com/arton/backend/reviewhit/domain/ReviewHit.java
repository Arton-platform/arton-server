package com.arton.backend.reviewhit.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewHit {
    private Long userId;
    private Long reviewId;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updatedDate;

    @Builder
    public ReviewHit(Long userId, Long reviewId, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
