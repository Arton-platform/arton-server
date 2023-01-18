package com.arton.backend.artist.adapter.out.repository;

import com.arton.backend.performance.domain.PerformanceType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.arton.backend.artist.adapter.out.repository.QArtistEntity.artistEntity;

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
}
