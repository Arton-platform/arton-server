package com.arton.backend.reviewhit.adapter.out.persistence.entitiy;

import com.arton.backend.infra.shared.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "review_hit", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "review_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(ReviewHitEntity.PK.class)
public class ReviewHitEntity extends BaseEntity {
    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Id
    @Column(name="review_id", insertable = false, updatable = false)
    private Long reviewId;

    @Builder
    public ReviewHitEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long userId, Long reviewId) {
        super(createdDate, updatedDate);
        this.userId = userId;
        this.reviewId = reviewId;
    }

    public static class PK implements Serializable {
        Long userId;
        Long reviewId;
    }

}
