package com.arton.backend.image.adapter.out.repository;

import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceMapper;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.adapter.out.repository.UserMapper;

/**
 * 자식 먼저 저장해야하므로
 * user가 있으면 안됨.
 */
public class PerformanceImageMapper {
    public static PerformanceImage toDomain(PerformanceImageEntity performanceImage) {
        return PerformanceImage.builder()
                .id(performanceImage.getId())
                .performance(PerformanceMapper.toDomain(performanceImage.getPerformance()))
                .imageUrl(performanceImage.getImageUrl())
                .createdDate(performanceImage.getCreatedDate())
                .updateDate(performanceImage.getUpdateDate())
                .build();
    }

    public static PerformanceImageEntity toEntity(PerformanceImage performanceImage) {
        return PerformanceImageEntity.builder()
                .id(performanceImage.getId())
                .performance(PerformanceMapper.toEntity(performanceImage.getPerformance()))
                .imageUrl(performanceImage.getImageUrl())
                .createdDate(performanceImage.getCreatedDate())
                .updateDate(performanceImage.getUpdateDate())
                .build();
    }
}
