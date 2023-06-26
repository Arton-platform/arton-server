package com.arton.backend.review.application.data;

import com.arton.backend.review.domain.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 마이 페이지에서 보여지는 사용자가 작성한 리뷰 정보
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {
    private Long id;
    private Long performanceId;
    private Long userId;
    private float starScore;
    private String createdDate;
    private String updatedDate;
    private String content;

    @Builder
    public ReviewDto(Long id, Long performanceId, Long userId, float starScore, String createdDate, String updatedDate, String content) {
        this.id = id;
        this.performanceId = performanceId;
        this.userId = userId;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.content = content;
    }

    public static ReviewDto toDtoFromDomain(Review review) {
        // 날짜 이쁘게
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return ReviewDto.builder()
                .id(review.getId())
                .performanceId(review.getPerformanceId())
                .userId(review.getUserId())
                .starScore(review.getStarScore())
                .content(review.getContent())
                .createdDate(review.getCreatedDate().format(formatter))
                .updatedDate(review.getUpdatedDate().format(formatter))
                .build();
    }
}
