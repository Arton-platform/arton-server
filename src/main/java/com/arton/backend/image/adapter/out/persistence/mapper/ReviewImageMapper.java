package com.arton.backend.image.adapter.out.persistence.mapper;

import com.arton.backend.image.adapter.out.persistence.entity.ReviewImageEntity;
import com.arton.backend.image.domain.ReviewImage;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;

/**
 * 자식 먼저 저장해야하므로
 * user가 있으면 안됨.
 */
public class ReviewImageMapper {
    public static ReviewImage toDomain(ReviewImageEntity reviewImage) {
        return ReviewImage.builder()
                .id(reviewImage.getId())
                .reviewId(reviewImage.getReview().getId())
                .imageUrl(reviewImage.getImageUrl())
                .createdDate(reviewImage.getCreatedDate())
                .updatedDate(reviewImage.getUpdatedDate())
                .build();
    }

    public static ReviewImageEntity toEntity(ReviewImage reviewImage) {
        return ReviewImageEntity.builder()
                .id(reviewImage.getId())
                .review(ReviewEntity.builder().id(reviewImage.getReviewId()).build())
                .imageUrl(reviewImage.getImageUrl())
                .createdDate(reviewImage.getCreatedDate())
                .updatedDate(reviewImage.getUpdatedDate())
                .build();
    }
}
