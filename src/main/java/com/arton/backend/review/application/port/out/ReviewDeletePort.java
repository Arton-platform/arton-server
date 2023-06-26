package com.arton.backend.review.application.port.out;

public interface ReviewDeletePort {
    void deleteUserAllReview(long userId);
    void deleteReview(long reviewId);
}
