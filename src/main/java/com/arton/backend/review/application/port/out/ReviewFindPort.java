package com.arton.backend.review.application.port.out;

import com.arton.backend.review.domain.Review;

import java.util.Optional;

public interface ReviewFindPort {
    boolean userHasReview(long reviewId, long userId);
    Optional<Review> findByIdAndUserId(long reviewId, long userId);
    Optional<Review> findByParentId(long parentId);
    Optional<Review> findById(long id);
}
