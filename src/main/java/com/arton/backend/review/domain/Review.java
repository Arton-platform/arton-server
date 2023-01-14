package com.arton.backend.review.domain;

import com.arton.backend.infra.shared.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Review extends Board {
    private long reviewId;
    private PerformanceEntity performance;
    private String content;
    private float starScore;

    @Builder
    public Review(long reviewId, PerformanceEntity performance, String content, float starScore, UserEntity user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.reviewId = reviewId;
        this.performance = performance;
        this.content = content;
        this.starScore = starScore;
    }
}
