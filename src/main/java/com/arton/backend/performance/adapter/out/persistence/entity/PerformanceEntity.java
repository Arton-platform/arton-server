package com.arton.backend.performance.adapter.out.persistence.entity;

import com.arton.backend.image.adapter.out.persistence.entity.PerformanceImageEntity;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performance.domain.ShowCategory;
import com.arton.backend.performer.adapter.out.persistence.entity.PerformerEntity;
import com.arton.backend.price.adapter.out.persistence.entity.PriceGradeEntity;
import com.arton.backend.price.domain.PriceGrade;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Performance")
@ToString
public class PerformanceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 제목 */
    private String title;
    /** 설명 */
    private String description;
    /** 좋아요 */
    private Long hit;
    /** 티켓 오픈 날짜 */
    private LocalDateTime ticketOpenDate;
    /** 티켓 종료 날짜 */
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
    @OneToMany(mappedBy = "performance", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<PerformerEntity> performers = new ArrayList<>();
    /** 좌석 등급 가격 */
    @OneToMany(mappedBy = "performance", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<PriceGradeEntity> priceGradeList = new ArrayList<>();
    private float starScore;
    /** 매수 제한 */
    private Integer purchaseLimit;
    /** 공연 상태 구분*/
    @Enumerated(EnumType.STRING)
    private ShowCategory showCategory;

    @Builder
    public PerformanceEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long id, String title, String description, Long hit, LocalDateTime startDate, LocalDateTime endDate, String musicalDateTime, String place, Integer runningTime, Integer interMission, Integer limitTime, Integer limitAge, String link, String etc, String imageUrl, PerformanceType performanceType, List<PerformerEntity> performers, List<PriceGradeEntity> priceGradeList, float starScore, LocalDateTime ticketOpenDate, LocalDateTime ticketEndDate, Integer purchaseLimit, ShowCategory showCategory) {
        super(createdDate, updatedDate);
        this.id = id;
        this.title = title;
        this.description = description;
        this.hit = hit;
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
        this.ticketEndDate = ticketEndDate;
        this.ticketOpenDate = ticketOpenDate;
        this.purchaseLimit = purchaseLimit;
        this.showCategory = showCategory;
    }
}
