package com.arton.backend.zzim.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.zzim.adapter.out.repository.PerformanceZzimCompoundKey;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "Performance_Zzim")
@ToString
@IdClass(PerformanceZzimCompoundKey.class)
public class PerformanceZzimEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;

    /**
     * User 에서도 performance zzim 기능이 있어서 가지고 있는지 한번 체크하고 넣어야 힘.
     * 만약 그냥 넣으면 무한 루프 에러 날수도.
     * @param user
     */
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
