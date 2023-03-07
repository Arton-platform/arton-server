package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.applicaiton.data.PerformanceDetailDtoV2;
import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface CustomPerformanceRepository {
    List<Performance> getPerformanceByStartDateASC();
    List<Performance> getPerformanceByEndDateASC();
    List<Performance> getPopularPerformances();
    PerformanceDetailDtoV2 getPerformanceDetails(Long id);
}
