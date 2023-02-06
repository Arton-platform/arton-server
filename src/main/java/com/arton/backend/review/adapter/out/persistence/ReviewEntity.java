package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.infra.shared.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class ReviewEntity<R extends CommonResponse> extends Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @ManyToOne
    @JoinColumn(name = "performanceId")
    private PerformanceEntity performance;
    private String content;
    private float starScore;

    @Builder
    public ReviewEntity(long reviewId, PerformanceEntity performance, String content, float starScore, UserEntity user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.reviewId = reviewId;
        this.performance = performance;
        this.content = content;
        this.starScore = starScore;
    }
}
