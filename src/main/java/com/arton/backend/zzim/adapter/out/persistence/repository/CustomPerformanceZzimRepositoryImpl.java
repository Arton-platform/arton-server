package com.arton.backend.zzim.adapter.out.persistence.repository;

import com.arton.backend.zzim.adapter.out.persistence.entity.PerformanceZzimEntity;
import com.arton.backend.zzim.adapter.out.persistence.mapper.PerformanceZzimMapper;
import com.arton.backend.zzim.domain.PerformanceZzim;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.zzim.adapter.out.persistence.entity.QPerformanceZzimEntity.performanceZzimEntity;


@Transactional
@RequiredArgsConstructor
public class CustomPerformanceZzimRepositoryImpl implements CustomPerformanceZzimRepository{

    private final JPAQueryFactory queryFactory;

    /**
     * 유저의 공연찜 리스트를 불러온다.
     * @param userId
     * @return
     */
    @Override
    public List<PerformanceZzim> getUsersFavoritePerformances(Long userId) {
        List<PerformanceZzimEntity> result = queryFactory.selectFrom(performanceZzimEntity)
                .where(performanceZzimEntity.user.id.eq(userId))
                .fetch();
        return Optional.ofNullable(result).orElseGet(Collections::emptyList)
                .stream().map(PerformanceZzimMapper::toDomain).collect(Collectors.toList());
    }

    /**
     * 유저의 공연찜 리스트 삭제
     * @param userId
     * @param ids
     * @return
     */
    @Override
    public long deleteUsersFavoritePerformances(Long userId, List<Long> ids) {
        return queryFactory.delete(performanceZzimEntity)
                .where(performanceZzimEntity.user.id.eq(userId),
                        performanceZzimEntity.id.in(ids))
                .execute();
    }
}
