package com.arton.backend.review.application.port.in;

public interface ReviewCountUseCase {
    Long performanceReviewCount(Long id);
    Long userReviewCount(Long id);
}
