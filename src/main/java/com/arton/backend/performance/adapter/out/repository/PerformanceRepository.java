package com.arton.backend.performance.adapter.out.repository;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long> {
    List<PerformanceEntity> findAllByPerformanceType(PerformanceType performanceType);
}
