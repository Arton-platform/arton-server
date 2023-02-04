package com.arton.backend.performance.adapter.out.persistence.document;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "performance")
@Mapping(mappingPath = "elastic/performance-mapping.json")
@Setting(settingPath = "elastic/performance-setting.json")
public class PerformanceDocument{
    @Id
    @Field(type = FieldType.Keyword)
    private Long id;
    /** 제목 */
    @Field(type = FieldType.Text)
    private String title;
    /** 공연 일시 */
    @Field(type = FieldType.Text)
    private String musicalDateTime;
    /** 공연장소 */
    @Field(type = FieldType.Text)
    private String place;
    /** 이미지 링크 */
    @Field(type = FieldType.Text)
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

    @Builder
    public PerformanceDocument(Long id, String title, String musicalDateTime, String place, String imageUrl, PerformanceType performanceType, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.musicalDateTime = musicalDateTime;
        this.place = place;
        this.imageUrl = imageUrl;
        this.performanceType = performanceType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PerformanceDocument from(PerformanceEntity performance) {
            return PerformanceDocument.builder()
                    .endDate(performance.getEndDate())
                    .id(performance.getId())
                    .imageUrl(performance.getImageUrl())
                    .performanceType(performance.getPerformanceType())
                    .musicalDateTime(performance.getMusicalDateTime())
                    .place(performance.getPlace())
                    .startDate(performance.getStartDate())
                    .title(performance.getTitle())
                    .build();
    }
}
