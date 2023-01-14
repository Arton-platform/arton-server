package com.arton.backend.price.domain;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import lombok.*;

import javax.persistence.*;

/**
 * 좌석 등급별 가격은 공연마다 다름
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@ToString
public class PriceGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long id;
    /** 좌석 등급 */
    private String gradeName;
    /** 좌석 가격 */
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;
}
