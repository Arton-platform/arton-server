package com.arton.backend.review.application.service;

import com.arton.backend.review.application.data.ReviewDto;
import com.arton.backend.review.application.port.in.ReviewCountUseCase;
import com.arton.backend.review.application.port.in.ReviewListUseCase;
import com.arton.backend.review.application.port.in.ReviewRegistUseCase;
import com.arton.backend.review.application.port.out.ReviewCountPort;
import com.arton.backend.review.application.port.out.ReviewListPort;
import com.arton.backend.review.application.port.out.ReviewRegistPort;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements ReviewListUseCase, ReviewRegistUseCase, ReviewCountUseCase {
    private final ReviewListPort reviewListPort;
    private final ReviewRegistPort reviewRegistPort;
    private final ReviewCountPort reviewCountPort;

    @Override
    public List<ReviewDto> reviewList(Long performanceId) {
        return reviewListPort.reviewList(performanceId).stream().map(ReviewDto::toDtoFromDomain).collect(Collectors.toList());
    }

    @Override
    public void regist(Review review) {
        reviewRegistPort.regist(review);
    }

    @Override
    public Long performanceReviewCount(Long id) {
        return reviewCountPort.getPerformanceReviewCount(id);
    }

    @Override
    public Long userReviewCount(Long id) {
        return reviewCountPort.getUserReviewCount(id);
    }
}
