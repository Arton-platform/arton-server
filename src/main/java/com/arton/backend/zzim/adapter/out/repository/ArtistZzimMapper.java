package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.artist.adapter.out.repository.ArtistMapper;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.adapter.out.repository.UserMapper;
import com.arton.backend.user.domain.User;
import com.arton.backend.zzim.domain.ArtistZzim;

public class ArtistZzimMapper {

    public static ArtistZzim toDomain(ArtistZzimEntity artistZzim) {
        return ArtistZzim.builder()
                .createdDate(artistZzim.getCreatedDate())
//                .user(UserMapper.toDomain(artistZzim.getUser()))
                .user(artistZzim.getUser().getId())
                .id(artistZzim.getId())
                .updateDate(artistZzim.getUpdateDate())
                .build();
    }

    public static ArtistZzimEntity toEntity(ArtistZzim artistZzim) {
        return ArtistZzimEntity.builder()
                .createdDate(artistZzim.getCreatedDate())
//                .user(UserMapper.toEntity(artistZzim.getUser()))
                .user(UserEntity.builder().id(artistZzim.getUser()).build())
                .id(artistZzim.getId())
                .updateDate(artistZzim.getUpdateDate())
                .build();
    }
}
