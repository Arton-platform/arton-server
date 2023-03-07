package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailDtoV2;
import com.arton.backend.performance.applicaiton.data.QImageDto;
import com.arton.backend.performance.applicaiton.data.QPerformanceDetailDtoV2;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.application.data.QPriceInfoDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

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
    public PerformanceDetailDtoV2 getPerformanceDetails(Long id) {
        Map<Long, PerformanceDetailDtoV2> resultMap = queryFactory
                .from(performanceEntity)
                .leftJoin(performanceImageEntity).on(performanceEntity.eq(performanceImageEntity.performance))
                .leftJoin(priceGradeEntity).on(performanceEntity.eq(priceGradeEntity.performance))
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
                        set(performanceImageEntity.imageUrl),
                        set(new QPriceInfoDto(priceGradeEntity.gradeName, priceGradeEntity.price)))
                ));
        PerformanceDetailDtoV2 performanceDetailDtoV2 = resultMap.get(id);
        performanceDetailDtoV2.fillData();
        return performanceDetailDtoV2;

    }
}
