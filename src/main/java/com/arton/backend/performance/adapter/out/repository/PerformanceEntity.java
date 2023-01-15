package com.arton.backend.performance.adapter.out.repository;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performer.adapter.out.repository.PerformerEntity;
import com.arton.backend.price.domain.PriceGrade;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
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
    private List<PriceGrade> priceGradeList = new ArrayList<>();
    private float starScore;
}
