package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.artist.adapter.out.repository.ArtistMapper;
import com.arton.backend.user.adapter.out.repository.UserMapper;
import com.arton.backend.zzim.domain.ArtistZzim;

public class ArtistZzimMapper {

    public static ArtistZzim toDomain(ArtistZzimEntity artistZzim) {
        return ArtistZzim.builder()
                .createdDate(artistZzim.getCreatedDate())
                .artist(ArtistMapper.toDomain(artistZzim.getArtist()))
                .user(UserMapper.toDomain(artistZzim.getUser()))
                .id(artistZzim.getId())
                .updateDate(artistZzim.getUpdateDate())
                .build();
    }

    public static ArtistZzimEntity toEntity(ArtistZzim artistZzim) {
        return ArtistZzimEntity.builder()
                .createdDate(artistZzim.getCreatedDate())
                .artist(ArtistMapper.toEntity(artistZzim.getArtist()))
                .user(UserMapper.toEntity(artistZzim.getUser()))
                .id(artistZzim.getId())
                .updateDate(artistZzim.getUpdateDate())
                .build();
    }
}
