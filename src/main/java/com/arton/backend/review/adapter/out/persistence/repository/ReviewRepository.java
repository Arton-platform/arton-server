package com.arton.backend.review.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByPerformanceOrderByStarScoreDesc(PerformanceEntity performanceEntity);
    List<ReviewEntity> findAllByUserOrderByCreatedDateDesc(UserEntity userEntity);
    Long countAllByUser_Id(Long userId);
    Long countAllByPerformance_Id(Long performanceId);
}
