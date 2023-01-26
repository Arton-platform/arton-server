package com.arton.backend.performance.adapter.out.repository;

import com.arton.backend.performance.applicaiton.port.out.PerformanceFeedPort;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.arton.backend.performance.adapter.out.repository.QPerformanceEntity.performanceEntity;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PerformanceFeedAdapter implements PerformanceFeedPort {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<PerformanceTicketOpenDateDto>> getAllPerformance() {
        LocalDateTime fiveDaysAfter = LocalDateTime.now().plusDays(5);

        return Optional.ofNullable(
            queryFactory.select(Projections.constructor(PerformanceTicketOpenDateDto.class, performanceEntity.ticketOpenDate, performanceEntity.id))
                    .from(performanceEntity)
                    .where(performanceEntity.ticketOpenDate.after(fiveDaysAfter))
                    .orderBy(performanceEntity.ticketOpenDate.asc())
                    .fetch()
        );
    }
}
