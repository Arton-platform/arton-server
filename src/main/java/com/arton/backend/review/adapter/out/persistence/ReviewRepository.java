package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity<CommonResponse>, Long> {
    Optional<List<ReviewEntity<CommonResponse>>> findAllByPerformanceOrderByStarScoreDesc(PerformanceEntity performanceEntity);
    Long countAllByUser_Id(Long userId);
    Long countAllByPerformance_Id(Long performanceId);
}
