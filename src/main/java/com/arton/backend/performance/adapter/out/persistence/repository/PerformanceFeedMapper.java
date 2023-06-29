package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import com.arton.backend.performance.domain.PerformanceFeed;

@Component
@NoArgsConstructor
public class PerformanceFeedMapper {

    public static PerformanceFeed toDomain(PerformanceFeedEntity performanceFeedEntity) {
        return PerformanceFeed.builder()
                .id(performanceFeedEntity.getId())
                .readStatus(performanceFeedEntity.getReadStatus())
                .user(
                    UserMapper.toDomain(
                        performanceFeedEntity.getUser()
                    )
                )
                .performance(
                    PerformanceMapper.toDomain(
                        performanceFeedEntity.getPerformance()
                    )
                )
                .build();
    }

    public static PerformanceFeedEntity toEntity(PerformanceFeed performanceFeed) {
        return PerformanceFeedEntity.builder()
                .id(performanceFeed.getId())
                .performanceEntity(
                    PerformanceMapper.toEntity(
                            performanceFeed.getPerformance()
                    )
                )
                .user(
                    UserMapper.toEntity(
                            performanceFeed.getUser()
                    )
                )
                .readStatus(performanceFeed.getReadStatus())
                .build();
    }
}
