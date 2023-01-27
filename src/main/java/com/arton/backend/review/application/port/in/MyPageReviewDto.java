package com.arton.backend.review.application.port.in;

import com.arton.backend.review.domain.Review;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 마이 페이지에서 보여지는 사용자가 작성한 리뷰 정보
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageReviewDto {
    private String title;
    private float starScore;
    private String createdDate;
    private String content;
    private List<String> images = new ArrayList<>();
    private Long hit;
    private Long reviewCount;

    public static MyPageReviewDto to(Review review) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return MyPageReviewDto.builder()
                .title(review.getPerformance().getTitle())
                .starScore(review.getStarScore())
                .createdDate(review.getCreatedDate().format(formatter))
                .content(review.getContent())
                .images(new ArrayList<>())
                .hit(review.getPerformance().getHit())
                .build();
    }
}
