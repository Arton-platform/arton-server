package com.arton.backend.review.application.port.in;

import com.arton.backend.review.application.data.CommonReviewDto;
import com.arton.backend.review.application.data.ReviewDto;
import com.arton.backend.review.application.data.ReviewForPerformanceDetailDto;

import java.util.List;

public interface ReviewListUseCase {
    /**
     * 공연 전체 리뷰 정보
     * 이미지 전체
     * 리뷰 전체
     * @param performanceId
     * @return
     */
    ReviewForPerformanceDetailDto reviewList(Long performanceId);
    List<CommonReviewDto> getReviewChilds(Long reviewId);
}
