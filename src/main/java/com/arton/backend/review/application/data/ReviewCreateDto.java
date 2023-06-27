package com.arton.backend.review.application.data;

import com.arton.backend.review.domain.Review;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * parentId를 명시하면 대댓글이고 아니라면 해당 댓글이 root가 됨.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCreateDto {
    private Long performanceId;
    private Long parentId;
    @NotBlank
    private String content;
    @PositiveOrZero
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
