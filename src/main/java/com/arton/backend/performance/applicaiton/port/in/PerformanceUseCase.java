package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.applicaiton.data.PerformanceDetailDto;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface PerformanceUseCase {
    List<Performance> getAllPerformances();
    List<PerformanceInterestDto> getZzimList();
    List<Performance> getMusicals();
    List<Performance> getConcerts();
    PerformanceDetailDto getOne(Long id);
}
