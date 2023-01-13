package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.application.port.out.ReviewListPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements ReviewListPort {

    private final ReviewRepository repository;
    @Override
    public Optional<List<ReviewEntity>> reviewList(Performance performance) {
        return repository.findAllByPerformanceOrderByStarScoreDesc(performance);
    }
}
