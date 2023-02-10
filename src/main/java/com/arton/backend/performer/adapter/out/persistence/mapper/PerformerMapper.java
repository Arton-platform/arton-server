package com.arton.backend.performer.adapter.out.persistence.mapper;

import com.arton.backend.artist.adapter.out.persistence.entity.ArtistEntity;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performer.adapter.out.persistence.entity.PerformerEntity;
import com.arton.backend.performer.domain.Performer;

public class PerformerMapper {

    public static Performer toDomain(PerformerEntity performer) {
        return Performer.builder()
                .createdDate(performer.getCreatedDate())
                .artist(performer.getArtist().getId())
                .performance(performer.getPerformance().getId())
                .id(performer.getId())
                .updateDate(performer.getUpdatedDate())
                .build();
    }

    public static PerformerEntity toEntity(Performer performer) {
        return PerformerEntity.builder()
                .createdDate(performer.getCreatedDate())
                .artist(ArtistEntity.builder().id(performer.getArtist()).build())
                .performance(PerformanceEntity.builder().id(performer.getPerformance()).build())
                .id(performer.getId())
                .updateDate(performer.getUpdatedDate())
                .build();
    }
}
