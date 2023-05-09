package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.artist.application.data.CommonArtistDto;
import com.arton.backend.price.application.data.PriceInfoDto;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Schema(description = "공연 상세 페이지 QueryDSL DTO V2(아티스트 정보 포함)")
public class PerformanceDetailQueryDslDtoV2 {
    private Long id;
    private String title;
    private String place;
    private String musicalDateTime;
    private Integer purchaseLimit;
    private Integer limitAge;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime ticketOpenDate;
    private LocalDateTime ticketEndDate;
    private Set<String> images = new LinkedHashSet<>();
    private Set<PriceInfoDto> prices = new LinkedHashSet<>();
    private Set<CommonArtistDto> artists = new LinkedHashSet<>();

    @Builder
    @QueryProjection
    public PerformanceDetailQueryDslDtoV2(Long id, String title, String place, String musicalDateTime, Integer purchaseLimit, Integer limitAge, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime ticketOpenDate, LocalDateTime ticketEndDate, Set<String> images, Set<PriceInfoDto> prices, Set<CommonArtistDto> artists) {
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
    }

    private void fillData(){
        prices = prices.stream().filter(PriceInfoDto::isCompleted).collect(Collectors.toSet());
    }

    private String getTextDayWithOutYear(LocalDateTime time) {
        String day = null;
        if (!ObjectUtils.isEmpty(time)) {
            String[] days = Optional.ofNullable(time).orElseGet(null).format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
            String textDay = Optional.ofNullable(time).orElseGet(null).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
            day = days[0]+"("+textDay+")"+" "+days[1];
        }
        return day;
    }

    private String getTextDayWithYear(LocalDateTime time) {
        String day = null;
        if (!ObjectUtils.isEmpty(time)) {
            String[] days = Optional.ofNullable(time).orElseGet(null).format(DateTimeFormatter.ofPattern("yyyy MM.dd HH:mm")).split(" ");
            String textDay = Optional.ofNullable(time).orElseGet(null).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
            day = days[0] + " " + days[1]+"("+textDay+")"+" "+days[2];
        }
        return day;
    }

    public PerformanceDetailDtoV2 toDto(){
        fillData();
        String textStartDay = getTextDayWithYear(startDate);
        String textEndDay = getTextDayWithYear(endDate);
        String ticketOpenDay = getTextDayWithYear(ticketOpenDate);
        String ticketEndDay = getTextDayWithYear(ticketEndDate);
        return PerformanceDetailDtoV2.builder()
                .id(getId())
                .images(images)
                .title(getTitle())
                .place(getPlace())
                .musicalDateTime(getMusicalDateTime())
                .prices(prices)
                .artists(artists)
                .purchaseLimit(getPurchaseLimit())
                .limitAge(getLimitAge())
                .startDate(StringUtils.hasText(textStartDay) ? textStartDay : "미정")
                .endDate(StringUtils.hasText(textEndDay) ? textEndDay : "미정")
                .ticketOpenDate(StringUtils.hasText(ticketOpenDay) ? ticketOpenDay : "미정")
                .ticketEndDate(StringUtils.hasText(ticketEndDay) ? ticketEndDay : "미정")
                .build();
    }


}
