package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.zzim.domain.PerformanceZzim;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.zzim.adapter.out.repository.QPerformanceZzimEntity.*;

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
}
