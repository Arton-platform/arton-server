package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.performance.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<List<ReviewEntity>> findAllByPerformanceOrderByStarScoreDesc(Performance performance);
}
