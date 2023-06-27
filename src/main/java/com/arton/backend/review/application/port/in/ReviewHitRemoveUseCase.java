package com.arton.backend.review.application.port.in;

public interface ReviewHitRemoveUseCase {
    void removeHit(long userId, long reviewId);
}
