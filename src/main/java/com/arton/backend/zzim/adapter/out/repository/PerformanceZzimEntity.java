package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class PerformanceZzimEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Builder
    public PerformanceZzimEntity(LocalDateTime createdDate, LocalDateTime updateDate, Long id, PerformanceEntity performance, UserEntity user) {
        super(createdDate, updateDate);
        this.id = id;
        this.performance = performance;
        this.user = user;
    }
}
