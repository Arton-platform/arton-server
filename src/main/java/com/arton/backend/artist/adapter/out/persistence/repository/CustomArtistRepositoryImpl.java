package com.arton.backend.artist.adapter.out.persistence.repository;

import com.arton.backend.artist.adapter.out.persistence.entity.ArtistEntity;
import com.arton.backend.artist.adapter.out.persistence.mapper.ArtistMapper;
import com.arton.backend.artist.application.data.CommonArtistDto;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.artist.adapter.out.persistence.entity.QArtistEntity.artistEntity;


@Transactional
@RequiredArgsConstructor
public class CustomArtistRepositoryImpl implements CustomArtistRepository{
    private final JPAQueryFactory queryFactory;

    /**
     * 출연작이 있는 artist중 공연 타입에 따라 artist를 반환한다.
     * @param performanceType
     * @return
     */
    @Override
    public List<ArtistEntity> getArtistByPerformanceType(PerformanceType performanceType) {
        return queryFactory.selectFrom(artistEntity)
                .where(artistEntity.performances.any().performance.performanceType.eq(performanceType))
                .fetch();
    }

    @Override
    public List<ArtistEntity> getArtistByPerformanceType(PerformanceType performanceType, Pageable pageable) {
        return queryFactory.selectFrom(artistEntity)
                .where(artistEntity.performances.any().performance.performanceType.eq(performanceType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ArtistEntity> getArtistByLimit(int offset, int limit) {
        return queryFactory.selectFrom(artistEntity).offset(offset).limit(limit).fetch();
    }
}
