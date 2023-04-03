package com.arton.backend.review.application.port.out;

public interface ReviewCountPort {
    Long getUserReviewCount(Long userId);
    Long getPerformanceReviewCount(Long performanceId);
}
