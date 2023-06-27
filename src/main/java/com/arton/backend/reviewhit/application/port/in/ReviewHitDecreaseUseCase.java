package com.arton.backend.reviewhit.application.port.in;

public interface ReviewHitDecreaseUseCase {
    void removeHit(long userId, long reviewId);
}
