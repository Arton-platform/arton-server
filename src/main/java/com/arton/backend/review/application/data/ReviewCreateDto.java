package com.arton.backend.review.application.data;

import com.arton.backend.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;

/**
 * parentId를 명시하면 대댓글이고 아니라면 해당 댓글이 root가 됨.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "리뷰 등록 DTO")
public class ReviewCreateDto {
    @Schema(description = "공연 ID")
    private Long performanceId;
    @Schema(description = "만약 대댓글을 달 경우 대댓을 달 리뷰 ID")
    private Long parentId;
    @NotBlank
    @Schema(description = "리뷰 내용")
    private String content;
    @Max(value = 5)
    @PositiveOrZero
    @Schema(description = "평점 0.0~5.0")
    private Float starScore;

    public Review toDomain() {
        return Review.builder()
                .performanceId(performanceId)
                .content(content)
                .starScore(starScore)
                .parentId(parentId)
                .commentsId(new ArrayList<>())
                .hit(0L)
                .build();
    }
}
