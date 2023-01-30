package com.arton.backend.image.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceImageRepository extends JpaRepository<PerformanceImageEntity, Long> {
    List<PerformanceImageEntity> findAllByPerformance_id(Long userId);
}
