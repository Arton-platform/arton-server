package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.application.data.PriceInfoDto;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        String ticketOpenDay = null;
        String ticketEndDay = null;
        boolean isTicketStart = false;
        boolean isTicketEnd = false;
        if (!ObjectUtils.isEmpty(performance.getTicketOpenDate())) {
            String[] ticketOpenDate = Optional.ofNullable(performance.getTicketOpenDate()).orElseGet(null).format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
            String textDay = Optional.ofNullable(performance.getTicketOpenDate()).orElseGet(null).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
            ticketOpenDay = ticketOpenDate[0]+"("+textDay+")"+" "+ticketOpenDate[1];
            isTicketStart = true;
        }
        if (!ObjectUtils.isEmpty(performance.getTicketEndDate())) {
            String[] ticketEndDate = Optional.ofNullable(performance.getTicketEndDate()).orElseGet(null).format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
            String textDay = Optional.ofNullable(performance.getTicketEndDate()).orElseGet(null).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
            ticketEndDay = ticketEndDate[0]+"("+textDay+")"+" "+ticketEndDate[1];
            isTicketEnd = true;
        }
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
                .ticketOpenDate(isTicketStart ? ticketOpenDay : "날짜 정보가 없습니다.")
                .ticketEndDate(isTicketEnd ? ticketEndDay : "날짜 정보가 없습니다.")
                .build();
    }
}
