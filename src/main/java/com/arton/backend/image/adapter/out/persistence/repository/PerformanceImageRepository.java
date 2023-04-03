package com.arton.backend.image.adapter.out.persistence.repository;

import com.arton.backend.image.adapter.out.persistence.entity.PerformanceImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceImageRepository extends JpaRepository<PerformanceImageEntity, Long> {
    List<PerformanceImageEntity> findAllByPerformance_id(Long userId);
    void deleteAllByPerformance_Id(Long performanceId);
}
