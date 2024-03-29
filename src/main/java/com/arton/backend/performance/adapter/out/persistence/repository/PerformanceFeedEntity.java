package com.arton.backend.performance.adapter.out.persistence.repository;

import javax.persistence.*;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;

import lombok.*;

@Entity
@Getter
@Table(name = "PerformanceFeed")
@NoArgsConstructor
public class PerformanceFeedEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;

    private Boolean readStatus;

    private int dDay;

    public void changeReadStatus(Boolean status) {
        this.readStatus = status;
    }

    @Builder
    public PerformanceFeedEntity(Long id, UserEntity user, PerformanceEntity performanceEntity, Boolean readStatus, int dDay) {
        this.id = id;
        this.user = user;
        this.performance = performanceEntity;
        this.readStatus = readStatus;
        this.dDay = dDay;
    }
}
