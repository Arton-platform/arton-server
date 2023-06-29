package com.arton.backend.review.application.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewForPerformanceDetailDto {
    private Long id;
    private Long userId;
    private String nickname;
    private float starScore;
    private String createdDate;
    private Long hit;
    private Long count;

    @Builder
    @QueryProjection
    public ReviewForPerformanceDetailDto(Long id, Long userId, String nickname, float starScore, String createdDate, Long hit, Long count) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.hit = hit;
        this.count = count;
    }
}