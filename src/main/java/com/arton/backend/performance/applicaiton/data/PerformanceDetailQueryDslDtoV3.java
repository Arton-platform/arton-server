package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.artist.application.data.CommonArtistDto;
import com.arton.backend.price.application.data.PriceInfoDto;
import com.arton.backend.review.application.data.CommonReviewQueryDslDto;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Schema(description = "공연 상세 페이지 QueryDSL DTO V3(아티스트 정보 포함)")
public class PerformanceDetailQueryDslDtoV3 {
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
    private Boolean zzim;
    private Set<String> images = new LinkedHashSet<>();
    private Set<PriceInfoDto> prices = new LinkedHashSet<>();
    private Set<CommonArtistDto> artists = new LinkedHashSet<>();
    private List<CommonReviewQueryDslDto> reviews = new ArrayList<>();

    @Builder
    @QueryProjection
    public PerformanceDetailQueryDslDtoV3(Long id, String title, String place, String musicalDateTime, Integer purchaseLimit, Integer limitAge, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime ticketOpenDate, LocalDateTime ticketEndDate, Boolean zzim, Set<String> images, Set<PriceInfoDto> prices, Set<CommonArtistDto> artists, List<CommonReviewQueryDslDto> reviews) {
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
        this.zzim = zzim;
        this.images = images;
        this.prices = prices;
        this.artists = artists;
        this.reviews = reviews;
    }

    private void fillData(){
        artists = artists.stream().filter(CommonArtistDto::isCompleted).collect(Collectors.toSet());
        prices = prices.stream().filter(PriceInfoDto::isCompleted).collect(Collectors.toSet());
        reviews = reviews.stream().filter(CommonReviewQueryDslDto::isCompleted).collect(Collectors.toList());
        reviews = reviews.stream().distinct().collect(Collectors.toList());
    }

    /**
     * yyyy mm dd (월)
     * @param time
     * @return
     */
    private String getTextYearDay(LocalDateTime time) {
        String day = null;
        if (!ObjectUtils.isEmpty(time)) {
            String[] days = getSplitDate(time);
            String textDay = getShortDay(time);
            day =  days[0] + " " + days[1]+"("+textDay+")";
        }
        return day;
    }

    /**
     * yyyy mm dd (월) hh:mm
     * @param time
     * @return
     */
    private String getTextYearDayTime(LocalDateTime time){
        String day = null;
        if (!ObjectUtils.isEmpty(time)) {
            String[] days = getSplitDate(time);
            String textDay = getShortDay(time);
            day = days[0] + " " + days[1]+"("+textDay+")"+" "+days[2];
        }
        return day;
    }

    private String[] getSplitDate(LocalDateTime time) {
        String day[] = {};
        if (!ObjectUtils.isEmpty(time)) {
            day =  Optional.ofNullable(time).orElseGet(null).format(DateTimeFormatter.ofPattern("yyyy MM.dd HH:mm")).split(" ");
        }
        return day;
    }

    private String getShortDay(LocalDateTime time) {
        String day = null;
        if (!ObjectUtils.isEmpty(time)) {
            day = Optional.ofNullable(time).orElseGet(null).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        }
        return day;
    }

    public PerformanceDetailDtoV3 toDto(){
        fillData();
        String textStartDay = getTextYearDay(startDate);
        String textEndDay = getTextYearDay(endDate);
        String ticketOpenDay = getTextYearDayTime(ticketOpenDate);
        String ticketEndDay = getTextYearDayTime(ticketEndDate);
        List<String> imageList = new ArrayList<>();
        for (String image : images) {
            imageList.add(image);
        }
        int iterSize = 2 - imageList.size();
        for (int i = 0; i < iterSize; i++) {
            imageList.add("");
        }
        return PerformanceDetailDtoV3.builder()
                .id(getId())
                .images(imageList)
                .title(getTitle())
                .place(getPlace())
                .musicalDateTime(getMusicalDateTime())
                .prices(prices)
                .artists(artists)
                .reviews(reviews.stream().map(CommonReviewQueryDslDto::toDto).collect(Collectors.toList()))
                .purchaseLimit(getPurchaseLimit())
                .limitAge(getLimitAge())
                .startDate(StringUtils.hasText(textStartDay) ? textStartDay : "미정")
                .endDate(StringUtils.hasText(textEndDay) ? textEndDay : "미정")
                .ticketOpenDate(StringUtils.hasText(ticketOpenDay) ? ticketOpenDay : "미정")
                .ticketEndDate(StringUtils.hasText(ticketEndDay) ? ticketEndDay : "미정")
                .reviewCount((long) reviews.size())
                .isZzim(zzim == null ? false : zzim)
                .build();
    }
}
