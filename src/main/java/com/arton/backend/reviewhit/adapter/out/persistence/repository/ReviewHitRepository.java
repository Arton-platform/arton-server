package com.arton.backend.reviewhit.adapter.out.persistence.repository;

import com.arton.backend.reviewhit.adapter.out.persistence.entitiy.ReviewHitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewHitRepository extends JpaRepository<ReviewHitEntity, ReviewHitEntity.PK> {
    Optional<ReviewHitEntity> findByUserIdAndReviewId(Long userId, Long reviewId);
    void deleteByUserIdAndReviewId(Long userId, Long reviewId);
    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);
}
