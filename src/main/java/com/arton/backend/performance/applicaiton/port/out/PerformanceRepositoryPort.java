package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface PerformanceRepositoryPort {
    List<Performance> findAllPerformances();
    List<Performance> findAllMusicals();
    List<Performance> findAllConcerts();
    List<Performance> findByIds(List<Long> ids);
    Performance save(Performance performance);
    void deletePerformance(Performance performance);
    void deleteById(Long id);
}
