package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDto;
import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface CustomPerformanceRepository {
    List<Performance> getPerformanceByStartDateASC();
    List<Performance> getPerformanceByEndDateASC();
    List<Performance> getPopularPerformances();
    PerformanceDetailQueryDslDto getPerformanceDetails(Long id);
}
