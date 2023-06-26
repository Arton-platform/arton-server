package com.arton.backend.review.application.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 마이 페이지에서 보여지는 사용자가 작성한 리뷰 정보
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageReviewQueryDSLDto {
    private Long reviewId;
    private Long performanceId;
    private Long userId;
    private String nickname;
    private String title;
    private float starScore;
    private LocalDateTime updatedDate;
    private String content;
    private Long hit;

    @Builder
    @QueryProjection
    public MyPageReviewQueryDSLDto(Long reviewId, Long performanceId, Long userId, String nickname, String title, float starScore, LocalDateTime updatedDate, String content, Long hit) {
        this.reviewId = reviewId;
        this.performanceId = performanceId;
        this.userId = userId;
        this.nickname = nickname;
        this.title = title;
        this.starScore = starScore;
        this.updatedDate = updatedDate;
        this.content = content;
        this.hit = hit;
    }

    /**
     * my page 리뷰 화면에 맞는 DTO로 변경
     * review count는 현재 대댓글 구현을 안해서 우선 0으로 가자..
     */
    public MyPageReviewDto toMyPageDTO() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return MyPageReviewDto.builder()
                .id(reviewId)
                .performanceId(performanceId)
                .userId(userId)
                .nickname(nickname)
                .title(title)
                .starScore(starScore)
                .updatedDate(updatedDate.format(formatter))
                .content(content)
                .hit(hit)
                .reviewCount(0L)
                .build();
    }
}