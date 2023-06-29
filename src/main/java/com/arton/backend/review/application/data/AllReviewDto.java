package com.arton.backend.review.application.data;

import com.arton.backend.review.domain.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 마이 페이지에서 보여지는 사용자가 작성한 리뷰 정보
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AllReviewDto {
    private Long id;
    private Long performanceId;
    private Long userId;
    private float starScore;
    private String createdDate;
    private String updatedDate;
    private String content;
    private List<ReviewDto> childs = new ArrayList<>();

    @Builder
    public AllReviewDto(Long id, Long performanceId, Long userId, float starScore, String createdDate, String updatedDate, String content, List<ReviewDto> childs) {
        this.id = id;
        this.performanceId = performanceId;
        this.userId = userId;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.content = content;
        this.childs = childs;
    }

    public static AllReviewDto toDtoFromDomain(Review review) {
        // 날짜 이쁘게
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return AllReviewDto.builder()
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
