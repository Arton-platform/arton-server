package com.arton.backend.performance.adapter.out.repository;

import org.springframework.stereotype.Component;

import com.arton.backend.performance.domain.PerformanceFeed;

@Component
public class PerformanceFeedMapper {
    public PerformanceFeed toDomain(PerformanceFeedEntity performanceFeedEntity) {
        return PerformanceFeed.builder()
                .id(performanceFeedEntity.getId())
                .readStatus(performanceFeedEntity.getReadStatus())
                .user(performanceFeedEntity.getUser())
                .performanceEntity(performanceFeedEntity.getPerformance())
                .build();
    }

    public PerformanceFeedEntity toEntity(PerformanceFeed performanceFeed) {
        return PerformanceFeedEntity.builder()
                .id(performanceFeed.getId())
                .performanceEntity(performanceFeed.getPerformance())
                .user(performanceFeed.getUser())
                .readStatus(performanceFeed.getReadStatus())
                .build();
    }
}
