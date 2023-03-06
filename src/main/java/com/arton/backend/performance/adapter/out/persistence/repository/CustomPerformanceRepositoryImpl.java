package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.image.adapter.out.persistence.entity.QPerformanceImageEntity;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.data.*;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.adapter.out.persistence.entity.QPriceGradeEntity;
import com.arton.backend.price.application.data.PriceInfoDto;
import com.arton.backend.price.application.data.QPriceInfoDto;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import static com.arton.backend.image.adapter.out.persistence.entity.QPerformanceImageEntity.*;
import static com.arton.backend.performance.adapter.out.persistence.entity.QPerformanceEntity.performanceEntity;
import static com.arton.backend.price.adapter.out.persistence.entity.QPriceGradeEntity.*;
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
    public List<Performance> getPerformanceByStartDateASC() {
        return Optional.ofNullable(queryFactory.selectFrom(performanceEntity)
                        .where(performanceEntity.startDate.goe(LocalDateTime.now()))
                .orderBy(performanceEntity.ticketOpenDate.asc())
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
    public List<Performance> getPerformanceByEndDateASC() {
        return Optional.ofNullable(queryFactory.selectFrom(performanceEntity)
                        .where(performanceEntity.startDate.goe(LocalDateTime.now()))
                        .orderBy(performanceEntity.ticketEndDate.asc())
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
    public List<Performance> getPopularPerformances() {
        return Optional.ofNullable(queryFactory.selectFrom(performanceEntity)
                        .where(performanceEntity.startDate.goe(LocalDateTime.now()))
                        .orderBy(performanceEntity.hit.desc())
                        .fetch())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PerformanceMapper::toDomain)
                .collect(toList());
    }

    @Override
    public List<PerformanceDetailDtoV2> getPerformanceDetails(Long id) {
        Map<Long, PerformanceDetailDtoV2> resultMap = queryFactory
                .from(performanceEntity)
                .join(performanceImageEntity).on(performanceEntity.eq(performanceImageEntity.performance))
                .join(priceGradeEntity).on(performanceEntity.eq(priceGradeEntity.performance))
                .where(performanceEntity.id.eq(id))
                .transform(groupBy(performanceEntity.id).as(new QPerformanceDetailDtoV2(
                        performanceEntity.id,
                        performanceEntity.title,
                        performanceEntity.place,
                        performanceEntity.musicalDateTime,
                        performanceEntity.purchaseLimit,
                        performanceEntity.limitAge,
                        performanceEntity.startDate,
                        performanceEntity.endDate,
                        set(new QImageDto(performanceImageEntity.imageUrl)),
                        set(new QPriceInfoDto(priceGradeEntity.gradeName, priceGradeEntity.price)))
                ));

        return resultMap.keySet().stream()
                .map(resultMap::get)
                .collect(toList());

    }
}
