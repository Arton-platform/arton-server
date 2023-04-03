package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDto;
import com.arton.backend.performance.domain.Performance;

import java.util.List;
import java.util.Optional;

public interface PerformanceRepositoryPort {
    List<Performance> findAll();
    List<Performance> findAllByLimit(int offset, int limit);
    List<Performance> findAllPerformances();
    List<Performance> findAllMusicals();
    List<Performance> findAllConcerts();
    List<Performance> findByIds(List<Long> ids);
    List<Performance> findByIds(List<Long> ids, int offset, int limit);
    List<Performance> findPopularPerformances(int offset, int limit);
    List<Performance> findStartingSoonPerformances(int offset, int limit);
    List<Performance> findEndingSoonPerformances(int offset, int limit);
    Optional<Performance> findById(Long id);
    boolean existsById(Long id);
    Optional<Performance> findOne(Long id);
    PerformanceDetailQueryDslDto getV2(Long id);
}
