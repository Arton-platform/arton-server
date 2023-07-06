package com.arton.backend.fcm;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "fcm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(length = 1000)
    private String token;

    @Builder
    public FcmEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long id, UserEntity user) {
        super(createdDate, updatedDate);
        this.id = id;
        this.user = user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
