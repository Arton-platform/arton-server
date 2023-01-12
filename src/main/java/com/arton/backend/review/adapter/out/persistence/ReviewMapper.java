package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.review.domain.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review toDomain(ReviewEntity entity){
        return Review.builder()
                .reviewId(entity.getReviewId())
                .content(entity.getContent())
                .starScore(entity.getStarScore())
                .hit(entity.getHit())
                .user(entity.getUser())
                .createdDate(entity.getCreatedDate())
                .updateDate(entity.getUpdateDate())
                .image(entity.getImage())
                .performance(entity.getPerformance())
                .build();
    }

    public ReviewEntity toEntity(Review review){
        return ReviewEntity.builder()
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .starScore(review.getStarScore())
                .hit(review.getHit())
                .updateDate(review.getUpdateDate())
                .createdDate(review.getCreatedDate())
                .updateDate(review.getUpdateDate())
                .image(review.getImage())
                .performance(review.getPerformance())
                .build();
    }
}
