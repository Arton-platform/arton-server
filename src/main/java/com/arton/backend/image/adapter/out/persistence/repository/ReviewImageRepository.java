package com.arton.backend.image.adapter.out.persistence.repository;

import com.arton.backend.image.adapter.out.persistence.entity.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Long> {
    List<ReviewImageEntity> findAllByReviewId(Long reviewId);
    @Modifying
    @Query("DELETE FROM ReviewImageEntity r where r.review.id =: reviewId")
    void deleteAllByReviewId(@Param(value = "reviewId") Long reviewId);
}
