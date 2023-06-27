package com.arton.backend.review.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ReviewEntity parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<ReviewEntity> children = new ArrayList<>();

    private String content;
    private float starScore;
    private Long hit;

    public ReviewEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long id, PerformanceEntity performance, UserEntity user, ReviewEntity parent, List<ReviewEntity> children, String content, float starScore, Long hit) {
        super(createdDate, updatedDate);
        this.id = id;
        this.performance = performance;
        this.user = user;
        this.parent = parent;
        this.children = children;
        this.content = content;
        this.starScore = starScore;
        this.hit = hit;
    }
}
