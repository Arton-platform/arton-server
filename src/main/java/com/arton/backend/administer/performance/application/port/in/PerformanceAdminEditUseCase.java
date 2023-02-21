package com.arton.backend.administer.performance.application.port.in;

import com.arton.backend.administer.performance.application.data.PerformanceAdminEditDto;

public interface PerformanceAdminEditUseCase {
    void editPerformance(Long id, PerformanceAdminEditDto performanceAdminEditDto);
}
