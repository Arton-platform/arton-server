package com.arton.backend.review.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String content;
    private float starScore;
    private Long hit;

    @Builder
    public ReviewEntity(LocalDateTime createdDate, LocalDateTime updatedDate, long id, PerformanceEntity performance, UserEntity user, String content, float starScore, Long hit) {
        super(createdDate, updatedDate);
        this.id = id;
        this.performance = performance;
        this.user = user;
        this.content = content;
        this.starScore = starScore;
        this.hit = hit;
    }
}
