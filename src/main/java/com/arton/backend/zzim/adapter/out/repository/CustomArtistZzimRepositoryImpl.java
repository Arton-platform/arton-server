package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.zzim.domain.ArtistZzim;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.zzim.adapter.out.repository.QArtistZzimEntity.artistZzimEntity;

@Transactional
@RequiredArgsConstructor
public class CustomArtistZzimRepositoryImpl implements CustomArtistZzimRepository{
    private final JPAQueryFactory queryFactory;

    /**
     * 유저의 아티스트 찜 리스트를 가져온다.
     * @param userId
     * @return
     */
    @Override
    public List<ArtistZzim> getUsersFavoriteArtists(Long userId) {
        List<ArtistZzimEntity> result = queryFactory.selectFrom(artistZzimEntity)
                .where(artistZzimEntity.user.id.eq(userId))
                .fetch();
        return Optional.ofNullable(result).orElseGet(Collections::emptyList)
                .stream().map(ArtistZzimMapper::toDomain).collect(Collectors.toList());
    }
}
