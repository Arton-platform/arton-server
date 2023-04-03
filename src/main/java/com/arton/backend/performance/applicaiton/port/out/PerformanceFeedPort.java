package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.performance.adapter.out.repository.PerformanceFeedEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceTicketOpenDateFromZzimDto;

import java.util.List;
import java.util.Optional;

public interface PerformanceFeedPort {
    Optional<List<PerformanceTicketOpenDateFromZzimDto>> getAllZzimList();

    void saveAll(List<PerformanceFeedEntity> entities);

    Optional<List<PerformanceFeedEntity>> getAllFeed();
}
