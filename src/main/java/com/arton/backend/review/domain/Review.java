package com.arton.backend.review.domain;

import com.arton.backend.common.entity.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Review extends Board {
    private long reviewId;
    private Performance performance;
    private String content;
    private float starScore;

    @Builder
    public Review(long reviewId, Performance performance, String content, float starScore, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.reviewId = reviewId;
        this.performance = performance;
        this.content = content;
        this.starScore = starScore;
    }
}
