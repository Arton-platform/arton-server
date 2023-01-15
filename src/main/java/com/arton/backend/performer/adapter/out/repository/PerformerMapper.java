package com.arton.backend.performer.adapter.out.repository;

import com.arton.backend.artist.adapter.out.repository.ArtistMapper;
import com.arton.backend.performance.adapter.out.repository.PerformanceMapper;
import com.arton.backend.performer.domain.Performer;

public class PerformerMapper {

    public static Performer toDomain(PerformerEntity performer) {
        return Performer.builder()
                .createdDate(performer.getCreatedDate())
                .artist(ArtistMapper.toDomain(performer.getArtist()))
                .performance(PerformanceMapper.toDomain(performer.getPerformance()))
                .id(performer.getId())
                .updateDate(performer.getUpdateDate())
                .build();
    }

    public static PerformerEntity toEntity(Performer performer) {
        return PerformerEntity.builder()
                .createdDate(performer.getCreatedDate())
                .artist(ArtistMapper.toEntity(performer.getArtist()))
                .performance(PerformanceMapper.toEntity(performer.getPerformance()))
                .id(performer.getId())
                .updateDate(performer.getUpdateDate())
                .build();
    }
}
