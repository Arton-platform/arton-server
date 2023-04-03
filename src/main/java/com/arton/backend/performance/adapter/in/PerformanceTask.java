package com.arton.backend.performance.adapter.in;

import com.arton.backend.performance.applicaiton.port.in.PerformanceFeedUseCase;
import com.arton.backend.performance.domain.PerformanceFeed;
import com.arton.backend.search.application.port.in.PerformanceSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PerformanceTask {

    private final PerformanceFeedUseCase performanceFeedUseCase;
    private final PerformanceSearchUseCase performanceSearchUseCase;

    @Scheduled(cron = "0 0 0 * * ?") // 1일 1회 00:00에 실행
    public void performanceSchedule() {
        log.info("[SCHEDULE] {}", "execute performanceSchedule");
        // 전체 공연중 티켓 오픈날자가 5일 이하, 찜한 공연의 유저, 피드 읽음 여부, 남은 날자
        List<PerformanceFeed> feeds = performanceFeedUseCase.getFeedFromZzim();
        // 찜 피드 테이블에 알림 내역 저장
        performanceFeedUseCase.saveAll(feeds);
        log.info("[SCHEDULE] {}", feeds);
        // 찜 한 대상들에게 전파
    }

    @Scheduled(cron = "0 0 0 * * ?") // 1일 1회 00:00에 실행
    public void saveDocuments() {
        log.info("[SCHEDULE] {}", "execute performanceDocumentSchedule");
        performanceSearchUseCase.saveAllDocuments();
        log.info("[SCHEDULE] finished...");
    }
}