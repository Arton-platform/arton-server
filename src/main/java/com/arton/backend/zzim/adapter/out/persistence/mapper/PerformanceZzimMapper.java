package com.arton.backend.zzim.adapter.out.persistence.mapper;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.zzim.adapter.out.persistence.entity.PerformanceZzimEntity;
import com.arton.backend.zzim.domain.PerformanceZzim;

public class PerformanceZzimMapper {

    public static PerformanceZzim toDomain(PerformanceZzimEntity performanceZzim) {
        return PerformanceZzim.builder()
                .createdDate(performanceZzim.getCreatedDate())
                .userId(performanceZzim.getUser().getId())
                .performanceId(performanceZzim.getPerformance().getId())
                .id(performanceZzim.getId())
                .updateDate(performanceZzim.getUpdatedDate())
                .build();
    }

    public static PerformanceZzimEntity toEntity(PerformanceZzim performanceZzim) {
        return PerformanceZzimEntity.builder()
                .createdDate(performanceZzim.getCreatedDate())
                .user(UserEntity.builder().id(performanceZzim.getUserId()).build())
                .performance(PerformanceEntity.builder().id(performanceZzim.getPerformanceId()).build())
                .id(performanceZzim.getId())
                .updateDate(performanceZzim.getUpdatedDate())
                .build();
    }
}
