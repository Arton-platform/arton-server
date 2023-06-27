package com.arton.backend.reviewhit.application.port.out;

public interface ReviewHitDeletePort {
    void deleteByUserAndReview(Long userId, Long reviewId);
}
