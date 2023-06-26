package com.arton.backend.review.application.port.in;

public interface ReviewDeleteUseCase {
    void delete(long userId, long reviewId);
    void deleteAllReviews(long userId);
}
