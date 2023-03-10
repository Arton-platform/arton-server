package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDto;
import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface CustomPerformanceRepository {
    List<Performance> getPerformanceByStartDateASC(int offset, int limit);
    List<Performance> getPerformanceByEndDateASC(int offset, int limit);
    List<Performance> getPopularPerformances(int offset, int limit);
    List<PerformanceEntity> findAllByLimit(int offset, int limit);
    List<PerformanceEntity> findZzimsByLimit(List<Long> ids, int offset, int limit);
    PerformanceDetailQueryDslDto getPerformanceDetails(Long id);
}
