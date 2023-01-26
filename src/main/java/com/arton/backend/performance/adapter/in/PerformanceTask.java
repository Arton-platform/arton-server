package com.arton.backend.performance.adapter.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.arton.backend.performance.applicaiton.port.in.PerformanceFeedUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceFeed;

@Component
@Slf4j
@RequiredArgsConstructor
public class PerformanceTask {

    private final PerformanceFeedUseCase performanceFeedUseCase;
//    private final PerformanceUseCase performanceUseCase;

    // @Scheduled(fixedRate = 100000)
    // @Scheduled(cron = "0 0 0 * * ?")
    public void performanceSchedule() {
        // 전체 공연을 조회를 한다.
        // 전체 공연중에 날자가 10일 이하로 남은 공연
        List<Performance> performanceList = performanceFeedUseCase.getAllPerformance();
//        List<Performance> performanes = performanceUseCase.getAllPerformances();
//        log.info("[performanceSchedule]", "{}", performanes);
        // 찜리스트에서 user id를 추출
        // 추출한 id를 이용하여 user id 추출
        // user id 이용하여 알림테이블에 알림 내역 저장
        // 알림테이블이 가지고 있어야하는 데이터
        // 알릴대상, 공연일자, 티켓팅오픈일자, 읽었는지 여부(PerformanceFeed)
    }
}
