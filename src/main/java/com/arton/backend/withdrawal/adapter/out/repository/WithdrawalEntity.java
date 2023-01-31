package com.arton.backend.withdrawal.adapter.out.repository;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 탈퇴사유 테이블
 */
@Entity
@Table(name = "Withdrawal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawalEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String comment;

    @Builder
    public WithdrawalEntity(LocalDateTime createdDate, LocalDateTime updateDate, Long id, UserEntity user, String comment) {
        super(createdDate, updateDate);
        this.id = id;
        this.user = user;
        this.comment = comment;
    }
}
