package com.arton.backend.review.application.port.in;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.domain.Review;

import java.util.List;

public interface ReviewListUseCase {
    List<Review> reviewList(Long id);
}
