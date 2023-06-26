package com.arton.backend.review.application.data;

import com.arton.backend.review.domain.Review;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCreateDto {
    private Long performanceId;
    @NotBlank
    private String content;
    private Float starScore;

    public Review toDomain() {
        return Review.builder()
                .performanceId(performanceId)
                .content(content)
                .starScore(starScore)
                .hit(0L)
                .build();
    }
}
