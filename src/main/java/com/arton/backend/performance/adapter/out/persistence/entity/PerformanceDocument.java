package com.arton.backend.performance.adapter.out.persistence.entity;

import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performer.adapter.out.persistence.entity.PerformerEntity;
import com.arton.backend.price.adapter.out.persistence.entity.PriceGradeEntity;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
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
}
