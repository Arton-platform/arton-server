package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.domain.Performance;

public interface PerformanceSaveUseCase {
    Performance save(Performance performance);
}
