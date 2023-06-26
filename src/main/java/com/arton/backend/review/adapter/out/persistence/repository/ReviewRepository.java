package com.arton.backend.review.adapter.out.persistence.repository;

import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByPerformanceIdOrderByStarScoreDesc(long performanceId);
    List<ReviewEntity> findAllByUserIdOrderByCreatedDateDesc(long userId);
    Long countAllByUser_Id(Long userId);
    Long countAllByPerformance_Id(Long performanceId);
}
