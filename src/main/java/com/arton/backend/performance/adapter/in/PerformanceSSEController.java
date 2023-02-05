package com.arton.backend.performance.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.applicaiton.port.in.PerformanceFeedUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSSEUseCase;
import com.arton.backend.performance.domain.PerformanceFeed;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/performance-sse")
public class PerformanceSSEController {
    private final PerformanceFeedUseCase performanceFeedUseCase;
    private final PerformanceSSEUseCase performanceSSEUseCase;

    @Operation(
        summary = "찜 피드 갱신 구독",
        description = "날자가 변경 될 때 마다 티켓오픈이 5일 이하로 남은 공연의 정보를 수신하기 위해 사용자를 추가한다."
    )
    @GetMapping(value = "/subscribe-performance-info")
    public SseEmitter subscribePerformanceInfo(){
        return performanceSSEUseCase.subscribePerformanceInfo();
    }

    @Operation(
        summary = "찜 피드 공지",
        description = "찜 피드를 구독한 사람들에게 공지"
    )
    @GetMapping(value = "/broadcast-performance" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<CommonResponse> broadcastPerformance(){
        // 전체 목록 호출(찜 피드)
        List<PerformanceFeed> performanceFeedList = performanceFeedUseCase.getAllFeed();
        // 구독한 유저에게 공지
        performanceSSEUseCase.broadcastPerformance(performanceFeedList);

        log.info("[broadcastPerformance] {}","SUCCESS");

        CommonResponse commonResponse = new CommonResponse("SUCCESS", HttpStatus.OK.value());
        return ResponseEntity.ok(commonResponse);
    }
}
