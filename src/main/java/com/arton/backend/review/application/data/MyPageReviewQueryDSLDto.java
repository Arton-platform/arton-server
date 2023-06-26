package com.arton.backend.review.application.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 마이 페이지에서 보여지는 사용자가 작성한 리뷰 정보
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageReviewQueryDSLDto {
    private Long reviewId;
    private Long performanceId;
    private Long userId;
    private String nickname;
    private String title;
    private float starScore;
    private String updatedDate;
    private String content;
    private Long hit;
    private Long reviewCount;

    @Builder
    @QueryProjection
    public MyPageReviewQueryDSLDto(Long reviewId, Long performanceId, Long userId, String nickname, String title, float starScore, String updatedDate, String content, Long hit, Long reviewCount) {
        this.reviewId = reviewId;
        this.performanceId = performanceId;
        this.userId = userId;
        this.nickname = nickname;
        this.title = title;
        this.starScore = starScore;
        this.updatedDate = updatedDate;
        this.content = content;
        this.hit = hit;
        this.reviewCount = reviewCount;
    }
}
