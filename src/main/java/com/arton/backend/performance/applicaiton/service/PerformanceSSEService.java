package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSSEUseCase;
import com.arton.backend.performance.domain.PerformanceFeed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PerformanceSSEService implements PerformanceSSEUseCase {

    private final SseEmitters sseEmitters;

    @Override
    public SseEmitter subscribePerformanceInfo() {
        // 알림대상에 현재 유저를 추가한다.
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitters.add(sseEmitter);

        try {
            sseEmitter.send(
                SseEmitter.event()
                    .name("subscribe")
                    .data("Subscribe for Performance Info!")
            );
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return sseEmitter;
    }

    // 구독한 모든 유저에게 공지
    @Override
    public void broadcastPerformance(List<PerformanceFeed> performanceFeedList) {
        ObjectMapper objectMapper = new ObjectMapper();
        sseEmitters.getEmitters()
            .forEach(sseEmitter -> {
                try {
                    String stringFeedList = objectMapper.writeValueAsString(performanceFeedList);
                    sseEmitter.send(
                        SseEmitter.event()
                                .name("performanceInfo")
                                .data(stringFeedList)
                    );
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new CustomException("JsonProcessingException", ErrorCode.JSON_PROCESSING_ERROR);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new CustomException("IOException", ErrorCode.IO_EXCEPTION);
                }
            });
    }
}
