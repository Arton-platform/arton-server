package com.arton.backend.review.application.service;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;
import com.arton.backend.review.adapter.out.persistence.ReviewMapper;
import com.arton.backend.review.application.port.in.ReviewListUseCase;
import com.arton.backend.review.application.port.in.ReviewRegistUseCase;
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
public class ReviewService implements ReviewListUseCase, ReviewRegistUseCase {
    private final ReviewListPort reviewListPort;
    private final ReviewRegistPort reviewRegistPort;
    private final ReviewMapper reviewMapper;
    @Override
    public List<Review> reviewList(Performance performance) {
        return reviewListPort.reviewList(performance).map(reviews -> reviews
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
}
