package com.arton.backend.zzim.application.data;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performance.domain.ShowCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "공연 찜 목록에 반환할 Dto")
@ToString
public class PerformanceZzimResponseDtoV2 {
    @Schema(description = "공연 ID")
    private Long id;
    @Schema(description = "공연 제목")
    private String title;
    @Schema(description = "이미지 링크")
    private String imageUrl;
    @Schema(description = "공연타입(뮤지컬/콘서트)")
    private PerformanceType performanceType;
    @Schema(description = "공연분류")
    private ShowCategory showCategory;
    @Schema(description = "장소")
    private String place;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public PerformanceZzimResponseDtoV2(Long id, String title, String imageUrl, PerformanceType performanceType, ShowCategory showCategory, String place, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.performanceType = performanceType;
        this.showCategory = showCategory;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PerformanceZzimResponseDtoV2 domainToDto(Performance performance) {
        return PerformanceZzimResponseDtoV2.builder()
                .id(performance.getPerformanceId())
                .title(performance.getTitle())
                .imageUrl(performance.getImageUrl())
                .performanceType(performance.getPerformanceType())
                .place(performance.getPlace())
                .startDate(performance.getStartDate())
                .endDate(performance.getEndDate())
                .showCategory(performance.getShowCategory())
                .build();
    }
}
