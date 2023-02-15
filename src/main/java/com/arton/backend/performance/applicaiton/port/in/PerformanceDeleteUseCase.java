package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.domain.Performance;

public interface PerformanceDeleteUseCase {
    void deletePerformance(Performance performance);
    void deletePerformance(Long id);
}
