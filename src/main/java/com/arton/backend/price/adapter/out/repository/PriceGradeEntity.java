package com.arton.backend.price.adapter.out.repository;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 좌석 등급별 가격은 공연마다 다름
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Price_Grade")
@ToString
public class PriceGradeEntity extends BaseEntity {
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

    @Builder
    public PriceGradeEntity(LocalDateTime createdDate, LocalDateTime updateDate, Long id, String gradeName, Long price, PerformanceEntity performance) {
        super(createdDate, updateDate);
        this.id = id;
        this.gradeName = gradeName;
        this.price = price;
        this.performance = performance;
    }
}
