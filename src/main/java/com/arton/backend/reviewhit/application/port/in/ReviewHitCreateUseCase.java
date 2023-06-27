package com.arton.backend.reviewhit.application.port.in;

public interface ReviewHitCreateUseCase {
    void addHit(long userId, long reviewId);
}
