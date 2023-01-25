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
@Table(name = "Performance_Zzim")
@ToString
public class PerformanceZzimEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;

    public void setUser(UserEntity user) {
        this.user = user;
        if (!user.getPerformanceZzims().contains(this)) {
            user.getPerformanceZzims().add(this);
        }
    }
    @Builder
    public PerformanceZzimEntity(LocalDateTime createdDate, LocalDateTime updateDate, Long id, UserEntity user, PerformanceEntity performance) {
        super(createdDate, updateDate);
        this.id = id;
        this.user = user;
        this.performance = performance;
    }
}
