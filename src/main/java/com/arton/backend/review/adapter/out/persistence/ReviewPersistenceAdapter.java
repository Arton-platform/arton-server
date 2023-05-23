package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.review.application.port.out.ReviewCountPort;
import com.arton.backend.review.application.port.out.ReviewFindPort;
import com.arton.backend.review.application.port.out.ReviewListPort;
import com.arton.backend.review.application.port.out.ReviewRegistPort;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements ReviewListPort, ReviewRegistPort, ReviewCountPort, ReviewFindPort {

    private final ReviewRepository repository;
    private final ReviewMapper reviewMapper;
    @Override
    public Optional<List<ReviewEntity<CommonResponse>>> reviewList(PerformanceEntity performanceEntity) {
        return repository.findAllByPerformanceOrderByStarScoreDesc(performanceEntity);
    }

    @Override
    public Optional<List<ReviewEntity<CommonResponse>>> userReviewList(UserEntity userEntity) {
        return repository.findAllByUserOrderByCreatedDateDesc(userEntity);
    }

    @Override
    public void regist(ReviewEntity<CommonResponse> entity) {
        repository.save(entity);
    }

    @Override
    public Long getUserReviewCount(Long userId) {
        return repository.countAllByUser_Id(userId);
    }

    @Override
    public Long getPerformanceReviewCount(Long performanceId) {
        return repository.countAllByPerformance_Id(performanceId);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return repository.findById(id).map(reviewMapper::toDomain);
    }
}
