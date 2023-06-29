package com.arton.backend.review.application.port.in;

import com.arton.backend.review.application.data.CommonReviewDto;
import com.arton.backend.review.application.data.ReviewDto;

import java.util.List;

public interface ReviewListUseCase {
    List<CommonReviewDto> reviewList(Long performanceId);
    List<CommonReviewDto> getReviewChilds(Long reviewId);
}
