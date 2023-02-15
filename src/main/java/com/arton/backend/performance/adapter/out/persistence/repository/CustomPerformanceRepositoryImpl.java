package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.domain.Performance;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.performance.adapter.out.persistence.entity.QPerformanceEntity.performanceEntity;

@Repository
@RequiredArgsConstructor
public class CustomPerformanceRepositoryImpl implements CustomPerformanceRepository{
    private final JPAQueryFactory queryFactory;

    /**
     * 공연 예매일 오픈이 빠른순 return
     * 하지만 유효한 공연이여야함.
     * 유효 -> 공연 시작일이 현재 날보다 늦어야함.
     * 빠르면 이미 예매는 불가.
     * @return
     */
    @Override
    public List<Performance> getPerformanceByStartDateASC() {
        return Optional.ofNullable(queryFactory.selectFrom(performanceEntity)
                        .where(performanceEntity.startDate.goe(LocalDateTime.now()))
                .orderBy(performanceEntity.ticketOpenDate.asc())
                .fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PerformanceMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * 공연 예매일 종료가 빠른순 return
     * 하지만 유효한 공연이여야함.
     * 유효 -> 공연 시작일이 현재 날보다 늦어야함.
     * 빠르면 이미 예매는 불가.
     * @return
     */
    @Override
    public List<Performance> getPerformanceByEndDateASC() {
        return Optional.ofNullable(queryFactory.selectFrom(performanceEntity)
                        .where(performanceEntity.startDate.goe(LocalDateTime.now()))
                        .orderBy(performanceEntity.ticketEndDate.asc())
                        .fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PerformanceMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * 유명한 공연 return
     * 하지만 유효한 공연이여야함.
     * 유효 -> 공연 시작일이 현재 날보다 늦어야함.
     * 빠르면 이미 예매는 불가.
     * @return
     */
    @Override
    public List<Performance> getPopularPerformances() {
        return Optional.ofNullable(queryFactory.selectFrom(performanceEntity)
                        .where(performanceEntity.startDate.goe(LocalDateTime.now()))
                        .orderBy(performanceEntity.hit.desc())
                        .fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PerformanceMapper::toDomain)
                .collect(Collectors.toList());
    }
}
