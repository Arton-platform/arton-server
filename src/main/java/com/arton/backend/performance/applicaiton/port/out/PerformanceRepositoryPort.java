package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface PerformanceRepositoryPort {
    List<PerformanceEntity> findAllPerformances();
    List<PerformanceEntity> findAllMusicals();
    List<PerformanceEntity> findAllConcerts();
    List<PerformanceEntity> findByIds(List<Long> ids);
    PerformanceEntity save(PerformanceEntity performance);
    void deletePerformance(PerformanceEntity performance);
    void deleteById(Long id);
}
