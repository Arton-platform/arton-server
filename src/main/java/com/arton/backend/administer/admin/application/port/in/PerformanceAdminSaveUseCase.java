package com.arton.backend.administer.admin.application.port.in;

import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import com.arton.backend.performance.domain.Performance;

public interface PerformanceAdminSaveUseCase {
    Performance addPerformance(PerformanceCreateDto performanceCreateDto);
}
