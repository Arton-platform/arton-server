package com.arton.backend.performance.adapter.out.persistence.entity;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performer.adapter.out.persistence.entity.PerformerEntity;
import com.arton.backend.performer.adapter.out.persistence.mapper.PerformerMapper;
import com.arton.backend.price.adapter.out.persistence.entity.PriceGradeEntity;
import com.arton.backend.price.adapter.out.persistence.mapper.PriceGradeMapper;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "performance")
@Mapping(mappingPath = "elastic/performance-mapping.json")
@Setting(settingPath = "elastic/performance-setting.json")
public class PerformanceDocument{
    @Id
    private Long id;
    /** 제목 */
    private String title;
    /** 설명 */
    private String description;
    /** 좋아요 */
    private Long hit;
    /** 공연 일시 */
    private String musicalDateTime;
    /** 공연장소 */
    private String place;
    /** 총 상영시간 */
    private Integer runningTime;
    /** 휴식시간 */
    private Integer interMission;
    /** 제한시간 */
    private Integer limitTime;
    /** 제한나이 */
    private Integer limitAge;
    /** 공연 링크 */
    private String link;
    /** 기타 */
    private String etc;
    /** 이미지 링크 */
    private String imageUrl;
    /** 뮤지컬 or 콘서트 */
    @Field(type = FieldType.Text)
    private PerformanceType performanceType;
    /** 공연 시작일 */
    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime startDate;
    /** 공연 종료일 */
    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime endDate;
    /** 등록일 */
    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdDate;
    /** 수정일 */
    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime updatedDate;
    /** 공연의 출연자들 리스트 */
    private List<PerformerEntity> performers = new ArrayList<>();
    /** 좌석 등급 가격 */
    private List<PriceGradeEntity> priceGradeList = new ArrayList<>();
    /** 평점 */
    private float starScore;

    @Builder
    public PerformanceDocument(Long id, String title, String description, Long hit, String musicalDateTime, String place, Integer runningTime, Integer interMission, Integer limitTime, Integer limitAge, String link, String etc, String imageUrl, PerformanceType performanceType, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createdDate, LocalDateTime updatedDate, List<PerformerEntity> performers, List<PriceGradeEntity> priceGradeList, float starScore) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.hit = hit;
        this.musicalDateTime = musicalDateTime;
        this.place = place;
        this.runningTime = runningTime;
        this.interMission = interMission;
        this.limitTime = limitTime;
        this.limitAge = limitAge;
        this.link = link;
        this.etc = etc;
        this.imageUrl = imageUrl;
        this.performanceType = performanceType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.performers = performers;
        this.priceGradeList = priceGradeList;
        this.starScore = starScore;
    }

    public static PerformanceDocument from(PerformanceEntity performance) {
            return PerformanceDocument.builder()
                    .description(performance.getDescription())
                    .endDate(performance.getEndDate())
                    .hit(performance.getHit())
                    .id(performance.getId())
                    .etc(performance.getEtc())
                    .imageUrl(performance.getImageUrl())
                    .performanceType(performance.getPerformanceType())
                    .interMission(performance.getInterMission())
                    .limitAge(performance.getLimitAge())
                    .performers(Optional.ofNullable(performance.getPerformers()).orElseGet(Collections::emptyList))
                    .limitTime(performance.getLimitTime())
                    .link(performance.getLink())
                    .musicalDateTime(performance.getMusicalDateTime())
                    .place(performance.getPlace())
                    .priceGradeList(Optional.ofNullable(performance.getPriceGradeList()).orElseGet(Collections::emptyList))
                    .runningTime(performance.getRunningTime())
                    .starScore(performance.getStarScore())
                    .startDate(performance.getStartDate())
                    .title(performance.getTitle())
                    .createdDate(performance.getCreatedDate())
                    .updatedDate(performance.getUpdatedDate())
                    .build();

    }
}
