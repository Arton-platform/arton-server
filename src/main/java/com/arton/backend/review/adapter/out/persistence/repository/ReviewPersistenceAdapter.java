package com.arton.backend.review.adapter.out.persistence.repository;

import com.arton.backend.review.adapter.out.persistence.mapper.ReviewMapper;
import com.arton.backend.review.application.port.out.ReviewCountPort;
import com.arton.backend.review.application.port.out.ReviewListPort;
import com.arton.backend.review.application.port.out.ReviewRegistPort;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements ReviewListPort, ReviewRegistPort, ReviewCountPort {
    private final ReviewRepository repository;
    private final ReviewMapper reviewMapper;
    @Override
    public List<Review> reviewList(long performanceId) {
        return Optional.ofNullable(repository.findAllByPerformanceIdOrderByStarScoreDesc(performanceId))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(reviewMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Review> userReviewList(long userId) {
        return Optional.ofNullable(repository.findAllByUserIdOrderByCreatedDateDesc(userId))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(reviewMapper::toDomain).collect(Collectors.toList());
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
}
