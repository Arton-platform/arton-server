package com.arton.backend.administer.performance.application.port.in;

import com.arton.backend.administer.performance.application.data.PerformanceAdminEditDto;

public interface PerformanceAdminUseCase {
    PerformanceAdminEditDto getPerformanceEditDto(Long performanceId);
}
