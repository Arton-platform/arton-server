package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.performance.adapter.out.repository.PerformanceTicketOpenDateDto;

import java.util.List;
import java.util.Optional;

public interface PerformanceFeedPort {
    Optional<List<PerformanceTicketOpenDateDto>> getAllPerformance();
}
