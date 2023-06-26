package com.arton.backend.review.application.data;

import com.arton.backend.review.domain.Review;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCreateDto {
    @NotBlank
    private Long performanceId;
    @NotBlank
    private String content;
    @NotBlank
    private float starScore;

    public Review toDomain() {
        return Review.builder()
                .performanceId(performanceId)
                .content(content)
                .starScore(starScore)
                .build();
    }
}