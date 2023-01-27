package com.arton.backend.review.application.service;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;
import com.arton.backend.review.adapter.out.persistence.ReviewMapper;
import com.arton.backend.review.application.port.in.ReviewCountUseCase;
import com.arton.backend.review.application.port.in.ReviewListUseCase;
import com.arton.backend.review.application.port.in.ReviewRegistUseCase;
import com.arton.backend.review.application.port.out.ReviewCountPort;
import com.arton.backend.review.application.port.out.ReviewListPort;
import com.arton.backend.review.application.port.out.ReviewRegistPort;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewListUseCase, ReviewRegistUseCase, ReviewCountUseCase {
    private final ReviewListPort reviewListPort;
    private final ReviewRegistPort reviewRegistPort;
    private final ReviewCountPort reviewCountPort;
    private final ReviewMapper reviewMapper;
    @Override
    public List<Review> reviewList(Long id) {
        PerformanceEntity performanceEntity = PerformanceEntity.builder()
                .id(id)
                .build();

        return reviewListPort.reviewList(performanceEntity).map(reviews -> reviews
                .stream()
                .map(review -> reviewMapper.toDomain(review))
                .collect(Collectors.toList())
        ).orElseGet(ArrayList::new);
    }

    @Override
    public Review regist(Review review) {
        ReviewEntity<CommonResponse> entity = reviewMapper.toEntity(review);
        reviewRegistPort.regist(entity);
        return review;
    }

    @Override
    public Long reviewCount(Long id) {
        return reviewCountPort.getReviewCount(id);
    }
}
