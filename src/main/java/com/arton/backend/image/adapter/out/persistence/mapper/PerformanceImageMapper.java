package com.arton.backend.image.adapter.out.persistence.mapper;

import com.arton.backend.image.adapter.out.persistence.entity.PerformanceImageEntity;
import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;

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
                .updateDate(performanceImage.getUpdatedDate())
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
