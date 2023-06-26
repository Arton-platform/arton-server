package com.arton.backend.review.application.data;

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
public class MyPageReviewDto {
    private Long id;
    private Long performanceId;
    private Long userId;
    private String nickname;
    private String title;
    private float starScore;
    private String updatedDate;
    private String content;
    private List<String> images = new ArrayList<>();
    private Long hit;
    private Long reviewCount;

    @Builder
    public MyPageReviewDto(Long id, Long performanceId, Long userId, String nickname, String title, float starScore, String updatedDate, String content, List<String> images, Long hit, Long reviewCount) {
        this.id = id;
        this.performanceId = performanceId;
        this.userId = userId;
        this.nickname = nickname;
        this.title = title;
        this.starScore = starScore;
        this.updatedDate = updatedDate;
        this.content = content;
        this.images = images;
        this.hit = hit;
        this.reviewCount = reviewCount;
    }


}