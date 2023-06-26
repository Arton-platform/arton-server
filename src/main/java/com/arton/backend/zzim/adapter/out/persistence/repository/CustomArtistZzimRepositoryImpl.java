package com.arton.backend.zzim.adapter.out.persistence.repository;

import com.arton.backend.zzim.adapter.out.persistence.entity.ArtistZzimEntity;
import com.arton.backend.zzim.adapter.out.persistence.mapper.ArtistZzimMapper;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.zzim.adapter.out.persistence.entity.QArtistZzimEntity.artistZzimEntity;


@Transactional
@RequiredArgsConstructor
public class CustomArtistZzimRepositoryImpl implements CustomArtistZzimRepository{
    private final JPAQueryFactory queryFactory;

    private BooleanExpression getEq(Long userId) {
        return artistZzimEntity.user.id.eq(userId);
    }
    /**
     * 유저의 아티스트 찜 리스트를 가져온다.
     * @param userId
     * @return
     */
    @Override
    public List<ArtistZzim> getUsersFavoriteArtists(Long userId) {
        List<ArtistZzimEntity> result = queryFactory.selectFrom(artistZzimEntity)
                .where(getEq(userId))
                .fetch();
        return Optional.ofNullable(result).orElseGet(Collections::emptyList)
                .stream().map(ArtistZzimMapper::toDomain).collect(Collectors.toList());
    }

    /**
     * 유저 찜목록 편집 기능
     * @param userId
     * @param ids
     * @return
     */
    @Override
    public long deleteUsersFavoriteArtists(Long userId, List<Long> ids) {
        return queryFactory.delete(artistZzimEntity)
                .where(getEq(userId),
                        artistZzimEntity.artist.id.in(ids))
                .execute();
    }
}
