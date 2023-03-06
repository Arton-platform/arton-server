package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.application.data.PriceInfoDto;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Schema(description = "공연 상세 페이지 DTO")
public class PerformanceDetailDtoV2 {
    private Long id;
    private String title;
    private String place;
    private String musicalDateTime;
    private Integer purchaseLimit;
    private Integer limitAge;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<ImageDto> images = new LinkedHashSet<>();
    private Set<PriceInfoDto> prices = new LinkedHashSet<>();


    @Builder
    @QueryProjection
    public PerformanceDetailDtoV2(Long id, String title, String place, String musicalDateTime, Integer purchaseLimit, Integer limitAge, LocalDateTime startDate, LocalDateTime endDate, Set<ImageDto> images, Set<PriceInfoDto> prices) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.musicalDateTime = musicalDateTime;
        this.purchaseLimit = purchaseLimit;
        this.limitAge = limitAge;
        this.startDate = startDate;
        this.endDate = endDate;
        this.images = images;
        this.prices = prices;
    }

    public static PerformanceDetailDtoV2 toDto(Performance performance, Set<ImageDto> images, Set<PriceInfoDto> prices) {
        return PerformanceDetailDtoV2.builder()
                .id(performance.getPerformanceId())
                .images(images)
                .title(performance.getTitle())
                .place(performance.getPlace())
                .musicalDateTime(performance.getMusicalDateTime())
                .prices(prices)
                .purchaseLimit(performance.getPurchaseLimit())
                .limitAge(performance.getLimitAge())
                .startDate(performance.getStartDate())
                .endDate(performance.getEndDate())
                .build();
    }

    public static PerformanceDetailDtoV2 toDto(Performance performance) {
        return PerformanceDetailDtoV2.builder()
                .id(performance.getPerformanceId())
                .images(new LinkedHashSet<>())
                .title(performance.getTitle())
                .place(performance.getPlace())
                .musicalDateTime(performance.getMusicalDateTime())
                .prices(new LinkedHashSet<>())
                .purchaseLimit(performance.getPurchaseLimit())
                .limitAge(performance.getLimitAge())
                .startDate(performance.getStartDate())
                .endDate(performance.getEndDate())
                .build();
    }
}
