package com.arton.backend.performance.domain;

import com.arton.backend.administer.performance.application.data.PerformanceAdminEditDto;
import com.arton.backend.performer.domain.Performer;
import com.arton.backend.price.domain.PriceGrade;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 대개 xxxToOne은 Fetch LAZY로
 * batch_size나 fetch join으로 N+1 해결하자
 * CASCADE REMOVE 통해 주 엔터티 삭제시 외래키 연관된 값도 삭제
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Performance {
    private Long performanceId;
    /** 제목 */
    private String title;
    /** 설명 */
    private String description;
    /** 좋아요 */
    private Long hit;
    /** 티켓 오픈일*/
    private LocalDateTime ticketOpenDate;
    /** 티켓 종료일*/
    private LocalDateTime ticketEndDate;
    /** 공연 시작일 */
    private LocalDateTime startDate;
    /** 공연 종료일 */
    private LocalDateTime endDate;
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
    /** 공연의 출연자들 리스트 */
    @ToString.Exclude
    @OneToMany(mappedBy = "performance")
    private List<Performer> performers = new ArrayList<>();
    /** 좌석 등급 가격 */
    @ToString.Exclude
    private List<PriceGrade> priceGradeList = new ArrayList<>();
    private float starScore;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updatedDate;
    /** 매수 제한 */
    private Integer purchaseLimit;
    /** 공연 상태 구분*/
    private ShowCategory showCategory;

    @Builder
    public Performance(Long performanceId, String title, String description, Long hit, LocalDateTime ticketOpenDate, LocalDateTime startDate, LocalDateTime endDate, String musicalDateTime, String place, Integer runningTime, Integer interMission, Integer limitTime, Integer limitAge, String link, String etc, String imageUrl, PerformanceType performanceType, List<Performer> performers, List<PriceGrade> priceGradeList, float starScore, LocalDateTime createdDate, LocalDateTime updateDate, LocalDateTime ticketEndDate, Integer purchaseLimit, ShowCategory showCategory) {
        this.performanceId = performanceId;
        this.title = title;
        this.description = description;
        this.hit = hit;
        this.ticketOpenDate = ticketOpenDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.musicalDateTime = musicalDateTime;
        this.place = place;
        this.runningTime = runningTime;
        this.interMission = interMission;
        this.limitTime = limitTime;
        this.limitAge = limitAge;
        this.link = link;
        this.etc = etc;
        this.imageUrl = imageUrl;
        this.performanceType = performanceType;
        this.performers = performers;
        this.priceGradeList = priceGradeList;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.updatedDate = updateDate;
        this.ticketEndDate = ticketEndDate;
        this.purchaseLimit = purchaseLimit;
        this.showCategory = showCategory;
    }

}
