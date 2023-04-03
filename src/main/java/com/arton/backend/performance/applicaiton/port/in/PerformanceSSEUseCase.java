package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.domain.PerformanceFeed;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface PerformanceSSEUseCase {
    SseEmitter subscribePerformanceInfo();

    void broadcastPerformance(List<PerformanceFeed> performanceFeedList);
}
