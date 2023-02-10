package com.arton.backend.artist.adapter.out.persistence.mapper;

import com.arton.backend.artist.adapter.out.persistence.entity.ArtistEntity;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performer.adapter.out.persistence.mapper.PerformerMapper;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArtistMapper {

    public static Artist toDomain(ArtistEntity artist) {
        return Artist.builder()
                .createdDate(artist.getCreatedDate())
                .updatedDate(artist.getUpdatedDate())
                .age(artist.getAge())
                .name(artist.getName())
                .profileImageUrl(artist.getProfileImageUrl())
                .performances(Optional.ofNullable(artist.getPerformances()).orElseGet(Collections::emptyList).stream().map(PerformerMapper::toDomain).collect(Collectors.toList()))
                .snsId(artist.getSnsId())
                .id(artist.getId())
                .build();
    }

    public static ArtistEntity toEntity(Artist artist) {
        return ArtistEntity.builder()
                .createdDate(artist.getCreatedDate())
                .updateDate(artist.getUpdatedDate())
                .age(artist.getAge())
                .name(artist.getName())
                .profileImageUrl(artist.getProfileImageUrl())
                .performances(Optional.ofNullable(artist.getPerformances()).orElseGet(Collections::emptyList).stream().map(PerformerMapper::toEntity).collect(Collectors.toList()))
                .snsId(artist.getSnsId())
                .id(artist.getId())
                .build();
    }
}
