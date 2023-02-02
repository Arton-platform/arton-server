package com.arton.backend.follow.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Follow", uniqueConstraints = @UniqueConstraint(columnNames = {"from_user", "to_user"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(FollowEntity.PK.class)
public class FollowEntity extends BaseEntity {

    /** 팔로워 즉 팔로잉을 하는 사람 */
    @Id
    @Column(name = "from_user", insertable = false, updatable = false)
    private Long fromUser;
    /** 팔로잉을 받는 사람 */
    @Id
    @Column(name = "to_user", insertable = false, updatable = false)
    private Long toUser;

    @Builder
    public FollowEntity(LocalDateTime createdDate, LocalDateTime updateDate, Long fromUser, Long toUser) {
        super(createdDate, updateDate);
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public static class PK implements Serializable {
        Long fromUser;
        Long toUser;
    }

}
