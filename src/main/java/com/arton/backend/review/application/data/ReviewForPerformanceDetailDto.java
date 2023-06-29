package com.arton.backend.review.application.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewForPerformanceDetailDto {
    private Long id;
    private Long userId;
    private String nickname;
    private float starScore;
    private String createdDate;
    private String content;
    private Long hit;
    private Long count;

    @Builder
    public ReviewForPerformanceDetailDto(Long id, Long userId, String nickname, float starScore, String createdDate, String content, Long hit, Long count) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.content = content;
        this.hit = hit;
        this.count = count;
    }
}