package com.arton.backend.performance.adapter.out.repository;

import com.arton.backend.image.adapter.out.repository.PerformanceImageMapper;
import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performer.adapter.out.repository.PerformerMapper;
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
                .priceGradeList(performance.getPriceGradeList())
                .runningTime(performance.getRunningTime())
                .starScore(performance.getStarScore())
                .startDate(performance.getStartDate())
                .title(performance.getTitle())
                .createdDate(performance.getCreatedDate())
                .updateDate(performance.getUpdateDate())
                .images(Optional.ofNullable(performance.getImages()).orElseGet(Collections::emptyList).stream().map(PerformanceImageMapper::toDomain).collect(Collectors.toList()))
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
                .priceGradeList(performance.getPriceGradeList())
                .runningTime(performance.getRunningTime())
                .starScore(performance.getStarScore())
                .startDate(performance.getStartDate())
                .title(performance.getTitle())
                .createdDate(performance.getCreatedDate())
                .updateDate(performance.getUpdateDate())
                .images(Optional.ofNullable(performance.getImages()).orElseGet(Collections::emptyList).stream().map(PerformanceImageMapper::toEntity).collect(Collectors.toList()))
                .build();
    }
}
