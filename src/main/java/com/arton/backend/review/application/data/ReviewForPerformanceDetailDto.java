package com.arton.backend.review.application.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewForPerformanceDetailDto {
    private Long id;
    private Long performanceId;
    private Long userId;
    private String profileImageUrl;
    private String nickname;
    private String title;
    private float starScore;
    private String createdDate;
    private String content;
    private List<String> images = new ArrayList<>();
    private Long hit;
    private Long reviewCount;

    @Builder
    public ReviewForPerformanceDetailDto(Long id, Long performanceId, Long userId, String profileImageUrl, String nickname, String title, float starScore, String createdDate, String content, List<String> images, Long hit, Long reviewCount) {
        this.id = id;
        this.performanceId = performanceId;
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.title = title;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.content = content;
        this.images = images;
        this.hit = hit;
        this.reviewCount = reviewCount;
    }
}