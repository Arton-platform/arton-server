package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.performance.adapter.out.repository.PerformanceMapper;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.adapter.out.repository.UserMapper;
import com.arton.backend.zzim.domain.PerformanceZzim;

public class PerformanceZzimMapper {

    public static PerformanceZzim toDomain(PerformanceZzimEntity performanceZzim) {
        return PerformanceZzim.builder()
                .createdDate(performanceZzim.getCreatedDate())
//                .user(UserMapper.toDomain(performanceZzim.getUser()))
                .userId(performanceZzim.getUser().getId())
                .id(performanceZzim.getId())
                .updateDate(performanceZzim.getUpdateDate())
                .build();
    }

    public static PerformanceZzimEntity toEntity(PerformanceZzim performanceZzim) {
        return PerformanceZzimEntity.builder()
                .createdDate(performanceZzim.getCreatedDate())
//                .user(UserMapper.toEntity(performanceZzim.getUser()))
                .user(UserEntity.builder().id(performanceZzim.getUserId()).build())
                .id(performanceZzim.getId())
                .updateDate(performanceZzim.getUpdateDate())
                .build();
    }
}
