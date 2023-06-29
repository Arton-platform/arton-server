package com.arton.backend.review.adapter.out.persistence.repository;

import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.adapter.out.persistence.mapper.ReviewMapper;
import com.arton.backend.review.application.data.CommonReviewQueryDslDto;
import com.arton.backend.review.application.port.out.*;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements ReviewListPort, ReviewRegistPort, ReviewCountPort, ReviewDeletePort, ReviewFindPort {
    private final ReviewRepository repository;
    private final ReviewMapper reviewMapper;
    @Override
    public List<ReviewEntity> reviewList(long performanceId) {
        return repository.getAllReviews(performanceId);
    }

    @Override
    public List<ReviewEntity> getReviewChilds(long reviewId) {
        return repository.getReviewChilds(reviewId);
    }

    @Override
    public List<Review> userReviewList(long userId) {
        return Optional.ofNullable(repository.findAllByUserIdOrderByCreatedDateDesc(userId))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(reviewMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<CommonReviewQueryDslDto> getUserReviewList(long userId) {
        return repository.getUserReviewList(userId);
    }

    @Override
    public void regist(Review review) {
        repository.save(reviewMapper.toEntity(review));
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
    public Long getChildReviewCount(Long parentId) {
        return repository.countAllByParentId(parentId);
    }

    @Override
    public void deleteUserAllReview(long userId) {
        repository.deleteAllByUserId(userId);
    }

    @Override
    public void deleteReview(long reviewId) {
        repository.deleteById(reviewId);
    }

    @Override
    public boolean userHasReview(long reviewId, long userId) {
        return repository.existsByIdAndUserId(reviewId, userId);
    }

    @Override
    public Optional<Review> findByIdAndUserId(long reviewId, long userId) {
        return repository.findByIdAndUserId(reviewId, userId).map(reviewMapper::toDomain);
    }

    @Override
    public Optional<Review> findByParentId(long parentId) {
        return repository.findByParentId(parentId).map(reviewMapper::toDomain);
    }

    @Override
    public Optional<Review> findById(long id) {
        return repository.findById(id).map(reviewMapper::toDomain);
    }
}
