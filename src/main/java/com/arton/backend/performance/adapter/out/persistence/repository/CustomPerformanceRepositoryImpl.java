package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDto;
import com.arton.backend.performance.applicaiton.data.QPerformanceDetailQueryDslDto;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.application.data.QPriceInfoDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.arton.backend.image.adapter.out.persistence.entity.QPerformanceImageEntity.performanceImageEntity;
import static com.arton.backend.performance.adapter.out.persistence.entity.QPerformanceEntity.performanceEntity;
import static com.arton.backend.price.adapter.out.persistence.entity.QPriceGradeEntity.priceGradeEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.set;
import static java.util.stream.Collectors.toList;

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
    public List<Performance> getPerformanceByStartDateASC(int offset, int limit) {
        return Optional.ofNullable(queryFactory.selectFrom(performanceEntity)
                        .where(performanceEntity.startDate.goe(LocalDateTime.now()))
                .orderBy(performanceEntity.ticketOpenDate.asc()).offset(offset).limit(limit)
                .fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PerformanceMapper::toDomain)
                .collect(toList());
    }

    /**
     * 공연 예매일 종료가 빠른순 return
     * 하지만 유효한 공연이여야함.
     * 유효 -> 공연 시작일이 현재 날보다 늦어야함.
     * 빠르면 이미 예매는 불가.
     * @return
     */
    @Override
    public List<Performance> getPerformanceByEndDateASC(int offset, int limit) {
        return Optional.ofNullable(queryFactory.selectFrom(performanceEntity)
                        .where(performanceEntity.startDate.goe(LocalDateTime.now()))
                        .orderBy(performanceEntity.ticketEndDate.asc()).offset(offset).limit(limit)
                        .fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PerformanceMapper::toDomain)
                .collect(toList());
    }

    /**
     * 유명한 공연 return
     * 하지만 유효한 공연이여야함.
     * 유효 -> 공연 시작일이 현재 날보다 늦어야함.
     * 빠르면 이미 예매는 불가.
     * @return
     */
    @Override
    public List<Performance> getPopularPerformances(int offset, int limit) {
        return Optional.ofNullable(queryFactory.selectFrom(performanceEntity)
                        .where(performanceEntity.startDate.goe(LocalDateTime.now()))
                        .orderBy(performanceEntity.hit.desc()).offset(offset).limit(limit)
                        .fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PerformanceMapper::toDomain)
                .collect(toList());
    }

    @Override
    public List<PerformanceEntity> findAllByLimit(int offset, int limit) {
        return queryFactory.selectFrom(performanceEntity)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    @Override
    public List<PerformanceEntity> findZzimsByLimit(List<Long> ids, int offset, int limit) {
        return queryFactory.selectFrom(performanceEntity)
                .where(performanceEntity.id.in(ids))
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    @Override
    public PerformanceDetailQueryDslDto getPerformanceDetails(Long id) {
        Map<Long, PerformanceDetailQueryDslDto> resultMap = queryFactory
                .from(performanceEntity)
                .leftJoin(performanceImageEntity).on(performanceEntity.eq(performanceImageEntity.performance))
                .leftJoin(priceGradeEntity).on(performanceEntity.eq(priceGradeEntity.performance))
                .where(performanceEntity.id.eq(id))
                .transform(groupBy(performanceEntity.id).as(new QPerformanceDetailQueryDslDto(
                        performanceEntity.id,
                        performanceEntity.title,
                        performanceEntity.place,
                        performanceEntity.musicalDateTime,
                        performanceEntity.purchaseLimit,
                        performanceEntity.limitAge,
                        performanceEntity.startDate,
                        performanceEntity.endDate,
                        performanceEntity.ticketOpenDate,
                        performanceEntity.ticketEndDate,
                        set(performanceImageEntity.imageUrl),
                        set(new QPriceInfoDto(priceGradeEntity.gradeName, priceGradeEntity.price)))
                ));
        return resultMap.get(id);
    }
}
