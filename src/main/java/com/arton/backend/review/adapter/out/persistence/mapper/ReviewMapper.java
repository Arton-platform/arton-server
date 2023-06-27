package com.arton.backend.review.adapter.out.persistence.mapper;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {

    public Review toDomain(ReviewEntity entity){
        return Review.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .starScore(entity.getStarScore())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .performanceId(entity.getPerformance().getId())
                .userId(entity.getUser().getId())
                .hit(entity.getHit())
                .parentId(ObjectUtils.isEmpty(entity.getParent())?null:entity.getParent().getId())
                .commentsId(entity.getChildren().stream().map(ReviewEntity::getId).collect(Collectors.toList()))
                .build();
    }

    public ReviewEntity toEntity(Review review){
        return ReviewEntity.builder()
                .id(review.getId())
                .content(review.getContent())
                .starScore(review.getStarScore())
                .performance(PerformanceEntity.builder().id(review.getPerformanceId()).build())
                .user(UserEntity.builder().id(review.getUserId()).build())
                .hit(review.getHit())
                .parent(review.getParentId() == null ? null : ReviewEntity.builder().id(review.getParentId()).build())
                .children(review.getCommentsId().stream().map(childId -> ReviewEntity.builder().id(childId).build()).collect(Collectors.toList()))
                .build();
    }
}
