package com.arton.backend.review.application.port.out;

import com.arton.backend.review.domain.Review;

import java.util.List;

public interface ReviewListPort {
    List<Review> reviewList(long performanceId);
    List<Review> userReviewList(long userId);
}
