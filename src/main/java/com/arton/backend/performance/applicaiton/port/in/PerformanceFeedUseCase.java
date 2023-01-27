package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceFeed;
import com.arton.backend.zzim.domain.PerformanceZzim;

import java.util.List;

public interface PerformanceFeedUseCase {

    List<PerformanceFeed> getFeedFromZzim();

    void saveAll(List<PerformanceFeed> feeds);
}
