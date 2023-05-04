package com.arton.backend.administer.performance.application.port.in;

import com.arton.backend.administer.performance.application.data.PerformanceAdminCreateDto;
import com.arton.backend.administer.performance.application.data.PerformanceAdminCreateResponseDto;
import com.arton.backend.performance.domain.Performance;

public interface PerformanceAdminSaveUseCase {
    PerformanceAdminCreateResponseDto addPerformance(PerformanceAdminCreateDto performanceCreateDto);
}
