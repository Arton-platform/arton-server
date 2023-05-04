package com.arton.backend.review.application.port.in;

import com.arton.backend.review.domain.Review;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 마이 페이지에서 보여지는 사용자가 작성한 리뷰 정보
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageReviewDto {
    private Long performanceId;
    private String title;
    private float starScore;
    private String createdDate;
    private String content;
    private List<String> images = new ArrayList<>();
    private Long hit;
    private Long reviewCount;

    @Builder
    public MyPageReviewDto(Long performanceId, String title, float starScore, String createdDate, String content, List<String> images, Long hit, Long reviewCount) {
        this.performanceId = performanceId;
        this.title = title;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.content = content;
        this.images = images;
        this.hit = hit;
        this.reviewCount = reviewCount;
    }

    public static MyPageReviewDto to(Review review) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return MyPageReviewDto.builder()
                .performanceId(review.getPerformance().getId())
                .title(review.getPerformance().getTitle())
                .starScore(review.getStarScore())
                .createdDate(review.getCreatedDate().format(formatter))
                .content(review.getContent())
                .images(new ArrayList<>())
                .hit(Optional.ofNullable(review.getPerformance().getHit()).orElse(0L))
                .reviewCount(0L)
                .build();
    }
}
