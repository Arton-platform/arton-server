package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.artist.application.data.QCommonArtistDto;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.data.*;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.price.application.data.QPriceInfoDto;
import com.arton.backend.review.application.data.QReviewForPerformanceQueryDslDetailDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.arton.backend.artist.adapter.out.persistence.entity.QArtistEntity.artistEntity;
import static com.arton.backend.image.adapter.out.persistence.entity.QPerformanceImageEntity.performanceImageEntity;
import static com.arton.backend.performance.adapter.out.persistence.entity.QPerformanceEntity.performanceEntity;
import static com.arton.backend.performer.adapter.out.persistence.entity.QPerformerEntity.performerEntity;
import static com.arton.backend.price.adapter.out.persistence.entity.QPriceGradeEntity.priceGradeEntity;
import static com.arton.backend.review.adapter.out.persistence.entity.QReviewEntity.reviewEntity;
import static com.arton.backend.user.adapter.out.persistence.entity.QUserEntity.userEntity;
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

    /**
     * 출연한 아티스트 정보까지 한번에
     * @param id
     * @return
     */
    @Override
    public PerformanceDetailQueryDslDtoV2 getPerformanceDetailsV2(Long id) {
        Map<Long, PerformanceDetailQueryDslDtoV2> result = queryFactory
                .from(performanceEntity)
                .leftJoin(performanceImageEntity).on(performanceEntity.eq(performanceImageEntity.performance))
                .leftJoin(priceGradeEntity).on(performanceEntity.eq(priceGradeEntity.performance))
                .leftJoin(performerEntity).on(performanceEntity.eq(performerEntity.performance))
                .leftJoin(artistEntity).on(performerEntity.artist.eq(artistEntity))
                .fetchJoin()
                .where(performanceEntity.id.eq(id))
                .transform(groupBy(performanceEntity.id).as(new QPerformanceDetailQueryDslDtoV2(
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
                        set(new QPriceInfoDto(priceGradeEntity.gradeName, priceGradeEntity.price)),
                        set(new QCommonArtistDto(artistEntity.id, artistEntity.name, artistEntity.profileImageUrl)))));
        if (result.isEmpty()) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return result.get(id);
    }

    @Override
    public PerformanceDetailQueryDslDtoV3 getPerformanceDetailsV3(Long id) {
        Map<Long, PerformanceDetailQueryDslDtoV3> result = queryFactory
                .from(performanceEntity)
                .leftJoin(performanceImageEntity).on(performanceEntity.eq(performanceImageEntity.performance))
                .leftJoin(priceGradeEntity).on(performanceEntity.eq(priceGradeEntity.performance))
                .leftJoin(performerEntity).on(performanceEntity.eq(performerEntity.performance))
                .leftJoin(artistEntity).on(performerEntity.artist.eq(artistEntity))
                .leftJoin(reviewEntity).on(performanceEntity.eq(reviewEntity.performance))
                .leftJoin(userEntity).on(reviewEntity.user.eq(userEntity))
                .fetchJoin()
                .where(performanceEntity.id.eq(id))
                .transform(groupBy(performanceEntity.id).as(new QPerformanceDetailQueryDslDtoV3(
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
                        set(new QPriceInfoDto(priceGradeEntity.gradeName, priceGradeEntity.price)),
                        set(new QCommonArtistDto(artistEntity.id, artistEntity.name, artistEntity.profileImageUrl)),
                        set(new QReviewForPerformanceQueryDslDetailDto(reviewEntity.id, userEntity.id, userEntity.nickname, reviewEntity.starScore, reviewEntity.createdDate, reviewEntity.content, reviewEntity.hit)))));
        if (result.isEmpty()) {
            System.out.println("result = is empty");
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return result.get(id);
    }

    /**
     * performance type에 따라 이미지 정보, 가격 정보까지 한번에 리턴하는 DTO
     * @param pageable
     * @param performanceType
     * @return
     */
    @Override
    public List<PerformanceDetailQueryDslDto> getPerformanceDetailsByType(Pageable pageable, PerformanceType performanceType) {
        return queryFactory
                .from(performanceEntity)
                .leftJoin(performanceImageEntity).on(performanceEntity.eq(performanceImageEntity.performance))
                .leftJoin(priceGradeEntity).on(performanceEntity.eq(priceGradeEntity.performance))
                .where(performanceEntity.performanceType.eq(performanceType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
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
                )).values().stream().collect(toList());
    }

    /**
     * 출연한 아티스트 정보까지 한번에
     * @param pageable
     * @param performanceType
     * @return
     */
    @Override
    public List<PerformanceDetailQueryDslDtoV2> getPerformanceAllDetailsByType(Pageable pageable, PerformanceType performanceType) {
        return queryFactory
                .from(performanceEntity)
                .leftJoin(performanceImageEntity).on(performanceEntity.eq(performanceImageEntity.performance))
                .leftJoin(priceGradeEntity).on(performanceEntity.eq(priceGradeEntity.performance))
                .leftJoin(performerEntity).on(performanceEntity.eq(performerEntity.performance))
                .leftJoin(artistEntity).on(performerEntity.artist.eq(artistEntity))
                .where(performanceEntity.performanceType.eq(performanceType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .transform(groupBy(performanceEntity.id).as(new QPerformanceDetailQueryDslDtoV2(
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
                        set(new QPriceInfoDto(priceGradeEntity.gradeName, priceGradeEntity.price)),
                        set(new QCommonArtistDto(artistEntity.id, artistEntity.name, artistEntity.profileImageUrl)))
                )).values().stream().collect(toList());
    }
}
