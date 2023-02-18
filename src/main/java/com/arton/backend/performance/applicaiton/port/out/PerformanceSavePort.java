package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.performance.domain.Performance;

public interface PerformanceSavePort {
    Performance save(Performance performance);
}
