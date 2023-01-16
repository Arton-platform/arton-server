package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.application.port.out.ReviewListPort;
import com.arton.backend.review.application.port.out.ReviewRegistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements ReviewListPort, ReviewRegistPort {

    private final ReviewRepository repository;
    @Override
    public Optional<List<ReviewEntity<CommonResponse>>> reviewList(Performance performance) {
        return repository.findAllByPerformanceOrderByStarScoreDesc(performance);
    }

    @Override
    public void regist(ReviewEntity<CommonResponse> entity) {
        repository.save(entity);
    }
}
