package com.arton.backend.price.adapter.out.persistence.mapper;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.adapter.out.persistence.entity.PriceGradeEntity;
import com.arton.backend.price.domain.PriceGrade;

public class PriceGradeMapper {
    public static PriceGrade toDomain(PriceGradeEntity priceGrade) {
        return PriceGrade.builder()
                .performance(Performance.builder().performanceId(priceGrade.getPerformance().getId()).build())
                .createdDate(priceGrade.getCreatedDate())
                .id(priceGrade.getId())
                .gradeName(priceGrade.getGradeName())
                .updateDate(priceGrade.getUpdatedDate())
                .price(priceGrade.getPrice())
                .build();
    }

    public static PriceGradeEntity toEntity(PriceGrade priceGrade) {
        return PriceGradeEntity.builder()
                .performance(PerformanceEntity.builder().id(priceGrade.getPerformance().getPerformanceId()).build())
                .createdDate(priceGrade.getCreatedDate())
                .id(priceGrade.getId())
                .gradeName(priceGrade.getGradeName())
                .updateDate(priceGrade.getUpdatedDate())
                .price(priceGrade.getPrice())
                .build();
    }
}
