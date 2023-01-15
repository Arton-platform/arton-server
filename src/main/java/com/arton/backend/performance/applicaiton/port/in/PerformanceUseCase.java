package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface PerformanceUseCase {
    List<PerformanceEntity> getAllPerformances();
    List<PerformanceInterestDto> getZzimList();
    List<PerformanceEntity> getMusicals();
    List<PerformanceEntity> getConcerts();
    PerformanceEntity save(PerformanceEntity performance);
    void deletePerformance(PerformanceEntity performance);
    void deletePerformance(Long id);
}
