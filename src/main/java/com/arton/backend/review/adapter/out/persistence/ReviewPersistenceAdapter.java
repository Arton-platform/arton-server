package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.application.port.out.ReviewCountPort;
import com.arton.backend.review.application.port.out.ReviewListPort;
import com.arton.backend.review.application.port.out.ReviewRegistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements ReviewListPort, ReviewRegistPort, ReviewCountPort {

    private final ReviewRepository repository;
    @Override
    public Optional<List<ReviewEntity<CommonResponse>>> reviewList(PerformanceEntity performanceEntity) {
        return repository.findAllByPerformanceOrderByStarScoreDesc(performanceEntity);
    }

    @Override
    public void regist(ReviewEntity<CommonResponse> entity) {
        repository.save(entity);
    }

    @Override
    public Long getReviewCount(Long userId) {
        return repository.countAllByUser_Id(userId);
    }
}
