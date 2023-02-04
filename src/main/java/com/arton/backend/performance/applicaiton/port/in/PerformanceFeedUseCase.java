package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.domain.PerformanceFeed;

import java.util.List;

public interface PerformanceFeedUseCase {

    List<PerformanceFeed> getFeedFromZzim();

    void saveAll(List<PerformanceFeed> feeds);

    List<PerformanceFeed> getAllFeed();
}
