package com.arton.backend.review.application.port.in;

public interface ReviewDeleteUseCase {
    Long delete(long userId, long reviewId);
    void deleteAllReviews(long userId);
}
