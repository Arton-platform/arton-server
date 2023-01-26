package com.arton.backend.performance.adapter.out.repository;

import javax.persistence.*;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.user.adapter.out.repository.UserEntity;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "PerformanceFeed")
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

    public void changeReadStatus(Boolean status) {
        this.readStatus = status;
    }

    @Builder
    public PerformanceFeedEntity(Long id, UserEntity user, PerformanceEntity performanceEntity, Boolean readStatus) {
        this.id = id;
        this.user = user;
        this.performance = performanceEntity;
        this.readStatus = readStatus;
    }
}
