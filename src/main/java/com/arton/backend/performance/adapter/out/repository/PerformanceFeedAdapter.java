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

import static com.arton.backend.performance.adapter.out.persistence.entity.QPerformanceEntity.performanceEntity;
import static com.arton.backend.zzim.adapter.out.persistence.entity.QPerformanceZzimEntity.performanceZzimEntity;
import static com.arton.backend.user.adapter.out.persistence.entity.QUserEntity.userEntity;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PerformanceFeedAdapter implements PerformanceFeedPort {

    private final JPAQueryFactory queryFactory;
    private final PerformanceFeedRepository repository;

    @Override
    public Optional<List<PerformanceTicketOpenDateFromZzimDto>> getAllZzimList() {
        LocalDateTime fiveDaysAfter = LocalDateTime.now().plusDays(5);

        return Optional.ofNullable(
             queryFactory.
                select(Projections.constructor(
                        PerformanceTicketOpenDateFromZzimDto.class
                        , performanceEntity
                        , userEntity
                    )
                )
                .from(performanceZzimEntity)
                .leftJoin(performanceZzimEntity.performance,performanceEntity)
                .leftJoin(performanceZzimEntity.user,userEntity)
                .where(performanceEntity.ticketOpenDate.between(LocalDateTime.now(), fiveDaysAfter))
                .fetch()
        );
    }

    @Override
    public void saveAll(List<PerformanceFeedEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    public Optional<List<PerformanceFeedEntity>> getAllFeed() {
        return Optional.ofNullable(repository.findAll());
    }
}
