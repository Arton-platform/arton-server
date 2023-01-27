package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.performance.adapter.out.repository.PerformanceFeedEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceFeedMapper;
import com.arton.backend.performance.adapter.out.repository.PerformanceMapper;
import com.arton.backend.performance.adapter.out.repository.PerformanceTicketOpenDateFromZzimDto;
import com.arton.backend.performance.applicaiton.port.out.PerformanceFeedPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceFeed;
import com.arton.backend.user.adapter.out.repository.UserMapper;
import com.arton.backend.zzim.domain.PerformanceZzim;
import org.springframework.stereotype.Service;

import com.arton.backend.performance.applicaiton.port.in.PerformanceFeedUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PerformanceFeedService implements PerformanceFeedUseCase {

    private final PerformanceFeedPort performanceFeedPort;
    private final PerformanceFeedMapper feedMapper;

    @Override
    public List<PerformanceFeed> getFeedFromZzim() {
        Optional<List<PerformanceTicketOpenDateFromZzimDto>> performances = performanceFeedPort.getAllZzimList();

         return performances.orElseGet(ArrayList::new)
                .stream()
                .map(performanceTicketOpenDateFromZzimDto -> PerformanceFeed.builder()
                        .readStatus(false)
                        .user(performanceTicketOpenDateFromZzimDto.getUser())
                        .performance(performanceTicketOpenDateFromZzimDto.getPerformance())
                        .dDay((int) ChronoUnit.DAYS.between(LocalDateTime.now(), performanceTicketOpenDateFromZzimDto.getPerformance().getTicketOpenDate()))
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public void saveAll(List<PerformanceFeed> feeds) {
        List<PerformanceFeedEntity> entities = feeds.stream()
                        .map(feedMapper::toEntity)
                        .collect(Collectors.toList());

        performanceFeedPort.saveAll(entities);
    }
}
