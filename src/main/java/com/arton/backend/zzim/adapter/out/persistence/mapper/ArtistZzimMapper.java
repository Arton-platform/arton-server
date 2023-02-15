package com.arton.backend.zzim.adapter.out.persistence.mapper;

import com.arton.backend.artist.adapter.out.persistence.entity.ArtistEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.zzim.adapter.out.persistence.entity.ArtistZzimEntity;
import com.arton.backend.zzim.domain.ArtistZzim;

public class ArtistZzimMapper {

    public static ArtistZzim toDomain(ArtistZzimEntity artistZzim) {
        return ArtistZzim.builder()
                .createdDate(artistZzim.getCreatedDate())
                .user(artistZzim.getUser().getId())
                .artist(artistZzim.getArtist().getId())
                .id(artistZzim.getId())
                .updateDate(artistZzim.getUpdatedDate())
                .build();
    }

    public static ArtistZzimEntity toEntity(ArtistZzim artistZzim) {
        return ArtistZzimEntity.builder()
                .createdDate(artistZzim.getCreatedDate())
                .user(UserEntity.builder().id(artistZzim.getUser()).build())
                .artist(ArtistEntity.builder().id(artistZzim.getArtist()).build())
                .id(artistZzim.getId())
                .updateDate(artistZzim.getUpdatedDate())
                .build();
    }
}
