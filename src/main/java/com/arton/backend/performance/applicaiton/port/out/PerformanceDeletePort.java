package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.performance.domain.Performance;

public interface PerformanceDeletePort {
    void deletePerformance(Performance performance);
    void deleteById(Long id);
}
