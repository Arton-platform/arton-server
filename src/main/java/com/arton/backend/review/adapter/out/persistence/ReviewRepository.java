package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity<CommonResponse>, Long> {
    Optional<List<ReviewEntity<CommonResponse>>> findAllByPerformanceOrderByStarScoreDesc(PerformanceEntity performanceEntity);
    Optional<List<ReviewEntity<CommonResponse>>> findAllByUserOrderByCreatedDateDesc(UserEntity userEntity);
    Long countAllByUser_Id(Long userId);
    Long countAllByPerformance_Id(Long performanceId);
}
