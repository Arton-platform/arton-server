package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.artist.application.data.CommonArtistDto;
import com.arton.backend.price.application.data.PriceInfoDto;
import com.arton.backend.review.application.data.ReviewForPerformanceDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Schema(description = "공연 상세 페이지 DTO(아티스트 정보 포함)")
public class PerformanceDetailDtoV3 {
    private Long id;
    private String title;
    private String place;
    private String musicalDateTime;
    private Integer purchaseLimit;
    private Integer limitAge;
    private String startDate;
    private String endDate;
    private String ticketOpenDate;
    private String ticketEndDate;
    private List<String> images = new ArrayList<>();
    private Set<PriceInfoDto> prices = new LinkedHashSet<>();
    private Set<CommonArtistDto> artists = new LinkedHashSet<>();
    private Set<ReviewForPerformanceDetailDto> reviews = new LinkedHashSet<>();

    @Builder
    public PerformanceDetailDtoV3(Long id, String title, String place, String musicalDateTime, Integer purchaseLimit, Integer limitAge, String startDate, String endDate, String ticketOpenDate, String ticketEndDate, List<String> images, Set<PriceInfoDto> prices, Set<CommonArtistDto> artists, Set<ReviewForPerformanceDetailDto> reviews) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.musicalDateTime = musicalDateTime;
        this.purchaseLimit = purchaseLimit;
        this.limitAge = limitAge;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ticketOpenDate = ticketOpenDate;
        this.ticketEndDate = ticketEndDate;
        this.images = images;
        this.prices = prices;
        this.artists = artists;
        this.reviews = reviews;
    }
}
