package com.arton.backend.reviewhit.adapter.out.persistence.mapper;

import com.arton.backend.reviewhit.adapter.out.persistence.entitiy.ReviewHitEntity;
import com.arton.backend.reviewhit.domain.ReviewHit;
import org.springframework.stereotype.Component;


public class ReviewHitMapper {
    public static ReviewHit toDomain(ReviewHitEntity reviewHit) {
        return ReviewHit.builder()
                .reviewId(reviewHit.getReviewId())
                .userId(reviewHit.getUserId())
                .createdDate(reviewHit.getCreatedDate())
                .updatedDate(reviewHit.getUpdatedDate())
                .build();
    }

    public static ReviewHitEntity toEntity(ReviewHit reviewHit) {
        return ReviewHitEntity.builder()
                .reviewId(reviewHit.getReviewId())
                .userId(reviewHit.getUserId())
                .createdDate(reviewHit.getCreatedDate())
                .updatedDate(reviewHit.getUpdatedDate())
                .build();
    }
}
