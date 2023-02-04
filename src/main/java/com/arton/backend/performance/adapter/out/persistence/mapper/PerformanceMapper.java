package com.arton.backend.performance.adapter.out.persistence.mapper;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performer.adapter.out.persistence.mapper.PerformerMapper;
import com.arton.backend.price.adapter.out.persistence.mapper.PriceGradeMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PerformanceMapper {

    public static Performance toDomain(PerformanceEntity performance) {
        return Performance.builder()
                .description(performance.getDescription())
                .endDate(performance.getEndDate())
                .hit(performance.getHit())
                .performanceId(performance.getId())
                .etc(performance.getEtc())
                .imageUrl(performance.getImageUrl())
                .performanceType(performance.getPerformanceType())
                .interMission(performance.getInterMission())
                .limitAge(performance.getLimitAge())
                .performers(Optional.ofNullable(performance.getPerformers()).orElseGet(Collections::emptyList).stream().map(PerformerMapper::toDomain).collect(Collectors.toList()))
                .limitTime(performance.getLimitTime())
                .link(performance.getLink())
                .musicalDateTime(performance.getMusicalDateTime())
                .place(performance.getPlace())
                .priceGradeList(Optional.ofNullable(performance.getPriceGradeList()).orElseGet(Collections::emptyList).stream().map(PriceGradeMapper::toDomain).collect(Collectors.toList()))
                .runningTime(performance.getRunningTime())
                .starScore(performance.getStarScore())
                .startDate(performance.getStartDate())
                .title(performance.getTitle())
                .createdDate(performance.getCreatedDate())
                .updateDate(performance.getUpdatedDate())
                .build();
    }

    public static PerformanceEntity toEntity(Performance performance) {
        return PerformanceEntity.builder()
                .description(performance.getDescription())
                .endDate(performance.getEndDate())
                .hit(performance.getHit())
                .id(performance.getPerformanceId())
                .etc(performance.getEtc())
                .imageUrl(performance.getImageUrl())
                .performanceType(performance.getPerformanceType())
                .interMission(performance.getInterMission())
                .limitAge(performance.getLimitAge())
                .performers(Optional.ofNullable(performance.getPerformers()).orElseGet(Collections::emptyList).stream().map(PerformerMapper::toEntity).collect(Collectors.toList()))
                .limitTime(performance.getLimitTime())
                .link(performance.getLink())
                .musicalDateTime(performance.getMusicalDateTime())
                .place(performance.getPlace())
                .priceGradeList(Optional.ofNullable(performance.getPriceGradeList()).orElseGet(Collections::emptyList).stream().map(PriceGradeMapper::toEntity).collect(Collectors.toList()))
                .runningTime(performance.getRunningTime())
                .starScore(performance.getStarScore())
                .startDate(performance.getStartDate())
                .title(performance.getTitle())
                .createdDate(performance.getCreatedDate())
                .updatedDate(performance.getUpdatedDate())
                .build();
    }
}
