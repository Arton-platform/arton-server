package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.price.application.data.PriceInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Schema(description = "공연 상세 페이지 DTO")
public class PerformanceDetailDto {
    private List<String> images = new ArrayList<>();
    private String title;
    private String place;
    private String musicalDateTime;
    private List<PriceInfoDto> prices = new ArrayList<>();
    private Integer purchaseLimit;
    private Integer limitAge;

    @Builder
    public PerformanceDetailDto(List<String> images, String title, String place, String musicalDateTime, List<PriceInfoDto> prices, Integer purchaseLimit, Integer limitAge) {
        this.images = images;
        this.title = title;
        this.place = place;
        this.musicalDateTime = musicalDateTime;
        this.prices = prices;
        this.purchaseLimit = purchaseLimit;
        this.limitAge = limitAge;
    }
}
