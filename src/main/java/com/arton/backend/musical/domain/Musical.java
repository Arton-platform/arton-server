package com.arton.backend.musical.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * price, artist 추가 필요
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Musical {
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
}
