package com.arton.backend.reviewhit.application.port.out;

import com.arton.backend.reviewhit.domain.ReviewHit;

import java.util.Optional;

public interface ReviewHitFindPort {
    Optional<ReviewHit> findByUserAndReview(Long userId, Long reviewId);
    boolean existByUserAndReview(Long userId, Long reviewId);
}
