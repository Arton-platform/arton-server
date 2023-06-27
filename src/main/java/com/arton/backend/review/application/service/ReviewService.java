package com.arton.backend.review.application.service;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
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
public class ReviewService implements ReviewListUseCase, ReviewRegistUseCase, ReviewCountUseCase, ReviewDeleteUseCase, ReviewEditUseCase, ReviewHitRemoveUseCase, ReviewHitAddUseCase {
    private final ReviewListPort reviewListPort;
    private final ReviewRegistPort reviewRegistPort;
    private final ReviewCountPort reviewCountPort;
    private final ReviewDeletePort reviewDeletePort;
    private final ReviewFindPort reviewFindPort;
    private final PerformanceRepositoryPort performanceRepositoryPort;
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
            throw new CustomException(ErrorCode.BAD_REQUEST.getMessage(), ErrorCode.BAD_REQUEST);
        }
        System.out.println("performance check start");
        // performance check
        performanceRepositoryPort.findById(reviewCreateDto.getPerformanceId()).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND));
        System.out.println("performance check finish");
        // parent check
        Review parent = null;
        if (reviewCreateDto.getParentId() != null){
            log.info("parent review {}", reviewCreateDto.getParentId());
            // 해당 부모아이디인 리뷰 id 가 존재하는지 체크
            parent = reviewFindPort.findById(reviewCreateDto.getParentId()).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND));
            if (parent.getPerformanceId() != reviewCreateDto.getPerformanceId()) {
                throw new CustomException(ErrorCode.REVIEW_PERFORMANCE_NOT_MATCHED.getMessage(), ErrorCode.REVIEW_PERFORMANCE_NOT_MATCHED);
            }
        }
        Review review = reviewCreateDto.toDomain();
        review.setUserId(userId);
        if (parent != null) {
            review.updateParent(parent);
        }
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

    @Override
    public void addHit(long userId, long reviewId) {

    }

    @Override
    public void removeHit(long userId, long reviewId) {

    }
}
