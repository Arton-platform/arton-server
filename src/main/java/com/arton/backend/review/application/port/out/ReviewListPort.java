package com.arton.backend.review.application.port.out;

import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.application.data.MyPageReviewQueryDSLDto;
import com.arton.backend.review.domain.Review;

import java.util.List;

public interface ReviewListPort {
    List<ReviewEntity> reviewList(long performanceId);
    List<ReviewEntity> getReviewChilds(long reviewId);
    List<Review> userReviewList(long userId);
    List<MyPageReviewQueryDSLDto> getUserReviewList(long userId);
}
