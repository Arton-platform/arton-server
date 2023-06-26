package com.arton.backend.review.adapter.out.persistence.repository;

import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, CustomReviewRepository {
    List<ReviewEntity> findAllByPerformanceIdOrderByStarScoreDesc(long performanceId);
    List<ReviewEntity> findAllByUserIdOrderByCreatedDateDesc(long userId);
    Long countAllByUser_Id(Long userId);
    Long countAllByPerformance_Id(Long performanceId);
    @Modifying
    @Query("DELETE from ReviewEntity review where review.user.id =: userId")
    void deleteAllByUserId(@Param("userId") long userId);
    boolean existsByIdAndUserId(long id, long userId);
}
