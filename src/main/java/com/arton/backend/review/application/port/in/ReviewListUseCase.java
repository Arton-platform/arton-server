package com.arton.backend.review.application.port.in;

import com.arton.backend.review.application.data.ReviewDto;

import java.util.List;

public interface ReviewListUseCase {
    List<ReviewDto> reviewList(Long performanceId);
    List<ReviewDto> getReviewChilds(Long reviewId);
}
