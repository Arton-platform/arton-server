package com.arton.backend.review.application.port.in;

public interface ReviewHitAddUseCase {
    void addHit(long userId, long reviewId);
}
