package com.arton.backend.review.application.service;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.review.application.data.ReviewCreateDto;
import com.arton.backend.review.application.data.ReviewDto;
import com.arton.backend.review.application.port.in.ReviewCountUseCase;
import com.arton.backend.review.application.port.in.ReviewDeleteUseCase;
import com.arton.backend.review.application.port.in.ReviewListUseCase;
import com.arton.backend.review.application.port.in.ReviewRegistUseCase;
import com.arton.backend.review.application.port.out.*;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements ReviewListUseCase, ReviewRegistUseCase, ReviewCountUseCase, ReviewDeleteUseCase {
    private final ReviewListPort reviewListPort;
    private final ReviewRegistPort reviewRegistPort;
    private final ReviewCountPort reviewCountPort;
    private final ReviewDeletePort reviewDeletePort;
    private final ReviewFindPort reviewFindPort;

    @Override
    public List<ReviewDto> reviewList(Long performanceId) {
        return reviewListPort.reviewList(performanceId).stream().map(ReviewDto::toDtoFromDomain).collect(Collectors.toList());
    }

    @Override
    public void regist(long userId, ReviewCreateDto reviewCreateDto) {
        Review review = reviewCreateDto.toDomain();
        review.setUserId(userId);
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

    @Override
    public void delete(long userId, long reviewId) {
        boolean userHasReview = reviewFindPort.userHasReview(reviewId, userId);
        if (!userHasReview) {
            throw new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND);
        }
        reviewDeletePort.deleteReview(reviewId);
    }

    @Override
    public void deleteAllReviews(long userId) {
        // delete
        reviewDeletePort.deleteUserAllReview(userId);
    }
}
