package com.arton.backend.review.application.service;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.review.application.data.ReviewCreateDto;
import com.arton.backend.review.application.data.ReviewDto;
import com.arton.backend.review.application.data.ReviewEditDto;
import com.arton.backend.review.application.port.in.*;
import com.arton.backend.review.application.port.out.*;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements ReviewListUseCase, ReviewRegistUseCase, ReviewCountUseCase, ReviewDeleteUseCase, ReviewEditUseCase {
    private final ReviewListPort reviewListPort;
    private final ReviewRegistPort reviewRegistPort;
    private final ReviewCountPort reviewCountPort;
    private final ReviewDeletePort reviewDeletePort;
    private final ReviewFindPort reviewFindPort;
    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");

    @Override
    public List<ReviewDto> reviewList(Long performanceId) {
        return reviewListPort.reviewList(performanceId).stream().map(ReviewDto::toDtoFromDomain).collect(Collectors.toList());
    }

    @Override
    public void regist(long userId, ReviewCreateDto reviewCreateDto) {
        log.info("review regist {}", reviewCreateDto);
        // validation
        if (reviewCreateDto.getPerformanceId() == null || reviewCreateDto.getStarScore() == null){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
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

    @Override
    public void edit(long userId, ReviewEditDto reviewEditDto) {
        if (reviewEditDto.getId() == null) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Review review = reviewFindPort.findByIdAndUserId(reviewEditDto.getId(), userId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND));
        review.editReview(reviewEditDto);
        // update
        reviewRegistPort.regist(review);
    }
}
