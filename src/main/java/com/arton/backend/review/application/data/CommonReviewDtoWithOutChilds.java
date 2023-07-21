package com.arton.backend.review.application.data;

import com.arton.backend.image.adapter.out.persistence.entity.ReviewImageEntity;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonReviewDtoWithOutChilds {
    private Long id;
    private Long parentId;
    private Long performanceId;
    private Long userId;
    private String profileImageUrl;
    private String nickname;
    private String title;
    private Integer starScore;
    private String createdDate;
    private String content;
    private List<String> images = new ArrayList<>();
    private Long hit;
    private Long reviewCount;

    public CommonReviewDtoWithOutChilds(Long id, Long parentId, Long performanceId, Long userId, String profileImageUrl, String nickname, String title, Integer starScore, String createdDate, String content, List<String> images, Long hit, Long reviewCount) {
        this.id = id;
        this.parentId = parentId;
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

    public static CommonReviewDtoWithOutChilds toDtoFromEntity(ReviewEntity review) {
        // 날짜 이쁘게
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return CommonReviewDtoWithOutChilds.builder()
                .id(review.getId())
                .parentId(ObjectUtils.isEmpty(review.getParent())? null : review.getParent().getId())
                .performanceId(review.getPerformance().getId())
                .userId(review.getUser().getId())
                .profileImageUrl(ObjectUtils.isEmpty(review.getUser().getUserImage()) ? "이미지 없음.." : review.getUser().getUserImage().getImageUrl())
                .nickname(review.getUser().getNickname())
                .starScore((int) review.getStarScore())
                .content(review.getContent())
                .createdDate(review.getCreatedDate().format(formatter))
                .images(review.getReviewImages().stream().map(ReviewImageEntity::getImageUrl).collect(Collectors.toList()))
                .hit(review.getHit())
                .reviewCount((long) review.getChildren().size())
                .build();
    }
}
