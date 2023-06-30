package com.arton.backend.review.application.data;

import com.arton.backend.image.adapter.out.persistence.entity.ReviewImageEntity;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 리뷰 공통 정보
 * 리뷰 상세를 누르기전에 굳이 대댓글을 보여줄 필요가 없음.
 * 이 부분은 프론트와 논의가 필요.
 */
@Data
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonReviewDto extends CommonReviewDtoWithOutChilds{
    private List<CommonReviewDto> childs = new ArrayList<>();

    public CommonReviewDto(Long id, Long parentId, Long performanceId, Long userId, String profileImageUrl, String nickname, String title, float starScore, String createdDate, String content, List<String> images, Long hit, Long reviewCount, List<CommonReviewDto> childs) {
        super(id, parentId, performanceId, userId, profileImageUrl, nickname, title, starScore, createdDate, content, images, hit, reviewCount);
        this.childs = childs;
    }

    public static CommonReviewDto toDtoFromEntity(ReviewEntity review) {
        // 날짜 이쁘게
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return CommonReviewDto.builder()
                .id(review.getId())
                .parentId(ObjectUtils.isEmpty(review.getParent())? null : review.getParent().getId())
                .title(review.getPerformance().getTitle())
                .childs(new ArrayList<>())
                .performanceId(review.getPerformance().getId())
                .userId(review.getUser().getId())
                .profileImageUrl(ObjectUtils.isEmpty(review.getUser().getUserImage()) ? "이미지 없음.." : review.getUser().getUserImage().getImageUrl())
                .nickname(review.getUser().getNickname())
                .starScore(review.getStarScore())
                .content(review.getContent())
                .createdDate(review.getCreatedDate().format(formatter))
                .images(review.getReviewImages().stream().map(ReviewImageEntity::getImageUrl).collect(Collectors.toList()))
                .hit(review.getHit())
                .reviewCount((long) review.getChildren().size())
                .build();
    }
}


