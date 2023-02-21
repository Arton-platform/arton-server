package com.arton.backend.administer.performance.application.port.in;

import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import com.arton.backend.performance.domain.Performance;

public interface PerformanceAdminSaveUseCase {
    Performance addPerformance(PerformanceCreateDto performanceCreateDto);
}
