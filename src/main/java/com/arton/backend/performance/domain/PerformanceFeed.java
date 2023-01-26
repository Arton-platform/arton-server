package com.arton.backend.performance.domain;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.user.adapter.out.repository.UserEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PerformanceFeed extends BaseEntity {

    private Long id;

    private UserEntity user;

    private PerformanceEntity performance;

    private Boolean readStatus;

    public void changeReadStatus(Boolean status) {
        this.readStatus = status;
    }

    @Builder
    public PerformanceFeed(Long id, UserEntity user, PerformanceEntity performanceEntity, Boolean readStatus) {
        this.id = id;
        this.user = user;
        this.performance = performanceEntity;
        this.readStatus = readStatus;
    }
}
