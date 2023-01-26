package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface PerformanceFeedUseCase {

    List<Performance> getAllPerformance();
}
