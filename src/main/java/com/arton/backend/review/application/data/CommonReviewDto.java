package com.arton.backend.review.application.data;

import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 리뷰 공통 정보
 * 리뷰 상세를 누르기전에 굳이 대댓글을 보여줄 필요가 없음.
 * 이 부분은 프론트와 논의가 필요.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonReviewDto {
    private Long id;
    private Long parentId;
    private List<CommonReviewDto> childs = new ArrayList<>();
    private Long performanceId;
    private Long userId;
    private String profileImageUrl;
    private String nickname;
    private String title;
    private float starScore;
    private String createdDate;
    private String content;
    private List<String> images = new ArrayList<>();
    private Long hit;
    private Long reviewCount;

    @Builder
    public CommonReviewDto(Long id, Long parentId, List<CommonReviewDto> childs, Long performanceId, Long userId, String profileImageUrl, String nickname, String title, float starScore, String createdDate, String content, List<String> images, Long hit, Long reviewCount) {
        this.id = id;
        this.parentId = parentId;
        this.childs = childs;
        this.performanceId = performanceId;
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.title = title;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.content = content;
        this.images = images;
        this.hit = hit;
        this.reviewCount = reviewCount;
    }

    public static CommonReviewDto toDtoFromEntity(ReviewEntity review) {
        // 날짜 이쁘게
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return CommonReviewDto.builder()
                .id(review.getId())
                .parentId(ObjectUtils.isEmpty(review.getParent())? null : review.getParent().getId())
                .childs(new ArrayList<>())
                .performanceId(review.getPerformance().getId())
                .userId(review.getUser().getId())
                .profileImageUrl(ObjectUtils.isEmpty(review.getUser().getUserImage()) ? "이미지 없음.." : review.getUser().getUserImage().getImageUrl())
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
