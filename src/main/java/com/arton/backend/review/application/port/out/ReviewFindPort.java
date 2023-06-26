package com.arton.backend.review.application.port.out;

import com.arton.backend.review.domain.Review;

public interface ReviewFindPort {
    boolean userHasReview(long reviewId, long userId);
}
