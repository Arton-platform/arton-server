package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.application.data.PriceInfoDto;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Schema(description = "공연 상세 페이지 DTO")
public class PerformanceDetailDto {
    private Long id;
    private String title;
    private String place;
    private String musicalDateTime;
    private Integer purchaseLimit;
    private Integer limitAge;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Schema(description = "티켓팅 오픈일")
    private String ticketOpenDate;
    @Schema(description = "공연 종료일")
    private String ticketEndDate;
    private List<ImageDto> images = new ArrayList<>();
    private List<PriceInfoDto> prices = new ArrayList<>();


    @Builder
    public PerformanceDetailDto(Long id, String title, String place, String musicalDateTime, Integer purchaseLimit, Integer limitAge, LocalDateTime startDate, LocalDateTime endDate, String ticketOpenDate, String ticketEndDate, List<ImageDto> images, List<PriceInfoDto> prices) {
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
    }

    public static PerformanceDetailDto toDto(Performance performance, List<ImageDto> images, List<PriceInfoDto> prices) {
        String[] ticketStartDate = performance.getTicketOpenDate().format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
        String ticketStartTextDay = performance.getTicketOpenDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        String[] ticketEndDate = performance.getTicketEndDate().format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
        String ticketEndTextDay = performance.getTicketEndDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        return PerformanceDetailDto.builder()
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
                .ticketOpenDate(ticketStartDate[0]+"("+ticketStartTextDay+")"+" "+ticketStartDate[1])
                .ticketEndDate(ticketEndDate[0]+"("+ticketEndTextDay+")"+" "+ticketEndDate[1])
                .build();
    }

    public static PerformanceDetailDto toDto(Performance performance) {
        String[] ticketStartDate = performance.getTicketOpenDate().format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
        String ticketStartTextDay = performance.getTicketOpenDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        String[] ticketEndDate = performance.getTicketEndDate().format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
        String ticketEndTextDay = performance.getTicketEndDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        return PerformanceDetailDto.builder()
                .id(performance.getPerformanceId())
                .images(new ArrayList<>())
                .title(performance.getTitle())
                .place(performance.getPlace())
                .musicalDateTime(performance.getMusicalDateTime())
                .prices(new ArrayList<>())
                .purchaseLimit(performance.getPurchaseLimit())
                .limitAge(performance.getLimitAge())
                .startDate(performance.getStartDate())
                .endDate(performance.getEndDate())
                .ticketOpenDate(ticketStartDate[0]+"("+ticketStartTextDay+")"+" "+ticketStartDate[1])
                .ticketEndDate(ticketEndDate[0]+"("+ticketEndTextDay+")"+" "+ticketEndDate[1])
                .build();
    }
}
