package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.performance.adapter.out.repository.PerformanceFeedEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceFeedMapper;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.adapter.out.repository.PerformanceTicketOpenDateFromZzimDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceFeedUseCase;
import com.arton.backend.performance.applicaiton.port.out.PerformanceFeedPort;
import com.arton.backend.performance.domain.PerformanceFeed;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public List<PerformanceFeed> getFeedFromZzim() {
        Optional<List<PerformanceTicketOpenDateFromZzimDto>> performances = performanceFeedPort.getAllZzimList();

         return performances.orElseGet(ArrayList::new)
                .stream()
                .map(performanceTicketOpenDateFromZzimDto -> PerformanceFeed.builder()
                        .readStatus(false)
                        .user(
                            UserMapper.toDomain(
                                performanceTicketOpenDateFromZzimDto.getUser()
                            )
                        )
                        .performance(
                            PerformanceMapper.toDomain(
                                performanceTicketOpenDateFromZzimDto.getPerformance()
                            )
                        )
                        // 지금날자 부터 5일 이내
                        .dDay((int) ChronoUnit.DAYS.between(LocalDateTime.now(), performanceTicketOpenDateFromZzimDto.getPerformance().getTicketOpenDate()))
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public void saveAll(List<PerformanceFeed> feeds) {
        List<PerformanceFeedEntity> entities = feeds.stream()
                        .map(PerformanceFeedMapper::toEntity)
                        .collect(Collectors.toList());

        performanceFeedPort.saveAll(entities);
    }

    @Override
    public List<PerformanceFeed> getAllFeed() {
        return performanceFeedPort.getAllFeed().orElseGet(ArrayList::new)
                .stream()
                .map(PerformanceFeedMapper::toDomain)
                .collect(Collectors.toList());
    }

}
