package com.arton.backend.price.adapter.out.persistence.mapper;

import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.price.adapter.out.persistence.entity.PriceGradeEntity;
import com.arton.backend.price.domain.PriceGrade;

public class PriceGradeMapper {
    public static PriceGrade toDomain(PriceGradeEntity priceGrade) {
        return PriceGrade.builder()
                .performance(PerformanceMapper.toDomain(priceGrade.getPerformance()))
                .createdDate(priceGrade.getCreatedDate())
                .id(priceGrade.getId())
                .gradeName(priceGrade.getGradeName())
                .updateDate(priceGrade.getUpdatedDate())
                .price(priceGrade.getPrice())
                .build();
    }

    public static PriceGradeEntity toEntity(PriceGrade priceGrade) {
        return PriceGradeEntity.builder()
                .performance(PerformanceMapper.toEntity(priceGrade.getPerformance()))
                .createdDate(priceGrade.getCreatedDate())
                .id(priceGrade.getId())
                .gradeName(priceGrade.getGradeName())
                .updateDate(priceGrade.getUpdatedDate())
                .price(priceGrade.getPrice())
                .build();
    }
}
