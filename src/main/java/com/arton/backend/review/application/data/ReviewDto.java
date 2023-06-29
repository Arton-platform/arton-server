package com.arton.backend.review.application.data;

import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
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
 * 사용자가 작성한 리뷰 정보
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {
    private Long id;
    private Long parentId;
    private List<ReviewDto> childs = new ArrayList<>();
    private Long performanceId;
    private Long userId;
    private String imageUrl;
    private String nickname;
    private float starScore;
    private String createdDate;
    private String content;
    private List<String> images = new ArrayList<>();
    private Long hit;
    private Long reviewCount;

    @Builder
    public ReviewDto(Long id, Long parentId, List<ReviewDto> childs, Long performanceId, Long userId, String imageUrl, String nickname, float starScore, String createdDate, String content, List<String> images, Long hit, Long reviewCount) {
        this.id = id;
        this.parentId = parentId;
        this.childs = childs;
        this.performanceId = performanceId;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.content = content;
        this.images = images;
        this.hit = hit;
        this.reviewCount = reviewCount;
    }

    public static ReviewDto toDtoFromDomain(Review review) {
        // 날짜 이쁘게
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return ReviewDto.builder()
                .id(review.getId())
                .parentId(review.getParentId())
                .performanceId(review.getPerformanceId())
                .userId(review.getUserId())
                .starScore(review.getStarScore())
                .content(review.getContent())
                .createdDate(review.getCreatedDate().format(formatter))
                .images(new ArrayList<>())
                .hit(0L)
                .reviewCount(0L)
                .build();
    }

    public static ReviewDto toDtoFromEntity(ReviewEntity review) {
        // 날짜 이쁘게
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return ReviewDto.builder()
                .id(review.getId())
                .parentId(review.getParent().getId())
                .childs(new ArrayList<>())
                .performanceId(review.getPerformance().getId())
                .userId(review.getUser().getId())
                .imageUrl(review.getUser().getUserImage().getImageUrl())
                .nickname(review.getUser().getNickname())
                .starScore(review.getStarScore())
                .content(review.getContent())
                .createdDate(review.getCreatedDate().format(formatter))
                .images(new ArrayList<>())
                .hit(review.getHit())
                .reviewCount((long) review.getChildren().size())
                .build();
    }
}
