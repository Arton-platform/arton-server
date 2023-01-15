package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.artist.adapter.out.repository.ArtistMapper;
import com.arton.backend.performance.adapter.out.repository.PerformanceMapper;
import com.arton.backend.user.adapter.out.repository.UserMapper;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;

public class PerformanceZzimMapper {

    public static PerformanceZzim toDomain(PerformanceZzimEntity performanceZzim) {
        return PerformanceZzim.builder()
                .createdDate(performanceZzim.getCreatedDate())
                .performance(PerformanceMapper.toDomain(performanceZzim.getPerformance()))
                .user(UserMapper.toDomain(performanceZzim.getUser()))
                .id(performanceZzim.getId())
                .updateDate(performanceZzim.getUpdateDate())
                .build();
    }

    public static PerformanceZzimEntity toEntity(PerformanceZzim performanceZzim) {
        return PerformanceZzimEntity.builder()
                .createdDate(performanceZzim.getCreatedDate())
                .performance(PerformanceMapper.toEntity(performanceZzim.getPerformance()))
                .user(UserMapper.toEntity(performanceZzim.getUser()))
                .id(performanceZzim.getId())
                .updateDate(performanceZzim.getUpdateDate())
                .build();
    }
}
