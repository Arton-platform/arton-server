package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.domain.PerformanceType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long>, CustomPerformanceRepository {
    List<PerformanceEntity> findAllByPerformanceType(PerformanceType performanceType);
    List<PerformanceEntity> findAllByPerformanceType(PerformanceType performanceType, Pageable pageable);
    boolean existsById(Long id);
}
