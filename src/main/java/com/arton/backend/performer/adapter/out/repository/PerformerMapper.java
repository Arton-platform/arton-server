package com.arton.backend.performer.adapter.out.repository;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performer.domain.Performer;

public class PerformerMapper {

    public static Performer toDomain(PerformerEntity performer) {
        return Performer.builder()
                .createdDate(performer.getCreatedDate())
//                .artist(ArtistMapper.toDomain(performer.getArtist()))
                .artist(performer.getArtist().getId())
//                .performance(PerformanceMapper.toDomain(performer.getPerformance()))
                .performance(performer.getPerformance().getId())
                .id(performer.getId())
                .updateDate(performer.getUpdateDate())
                .build();
    }

    public static PerformerEntity toEntity(Performer performer) {
        return PerformerEntity.builder()
                .createdDate(performer.getCreatedDate())
//                .artist(ArtistMapper.toEntity(performer.getArtist()))
                .artist(ArtistEntity.builder().id(performer.getArtist()).build())
//                .performance(PerformanceMapper.toEntity(performer.getPerformance()))
                .performance(PerformanceEntity.builder().id(performer.getPerformance()).build())
                .id(performer.getId())
                .updateDate(performer.getUpdateDate())
                .build();
    }
}
