package com.arton.backend.image.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 이미지가 외래키를 갖어야함.
 * 만약 공연이 외래키를 갖는다면 데이터 정규성에 어울리지 않는듯함..
 */
@Entity
@Table(name = "Performance_Image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user"})
public class PerformanceImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;

    public void setPerformance(PerformanceEntity performance) {
        this.performance = performance;
    }

    @Builder
    public PerformanceImageEntity(LocalDateTime createdDate, LocalDateTime updateDate, Long id, String imageUrl, PerformanceEntity performance) {
        super(createdDate, updateDate);
        this.id = id;
        this.imageUrl = imageUrl;
        this.performance = performance;
    }
}
