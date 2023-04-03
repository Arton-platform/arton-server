package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.applicaiton.port.in.PerformanceFeedUseCase;
import com.arton.backend.performance.domain.PerformanceFeed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PerformanceSSEServiceTest {

    @Autowired
    PerformanceSSEService sseService;

    @Autowired
    PerformanceFeedUseCase performanceFeedUseCase;

    @Autowired
    SseEmitters sseEmitters;

    @Test
    void subscribePerformanceInfo() {
        sseService.subscribePerformanceInfo();
        int emitterCount = sseEmitters.getEmitters().size();
        System.out.println("emitter count : " + emitterCount);
        assertNotEquals(0, emitterCount);
    }
}