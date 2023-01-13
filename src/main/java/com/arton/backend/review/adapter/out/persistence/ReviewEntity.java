package com.arton.backend.review.adapter.out.persistence;

import com.arton.backend.infra.shared.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.domain.User;
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
public class ReviewEntity extends Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @ManyToOne
    @JoinColumn(name = "performanceId")
    private Performance performance;
    private String content;
    private float starScore;

    @Builder
    public ReviewEntity(long reviewId, Performance performance, String content, float starScore, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.reviewId = reviewId;
        this.performance = performance;
        this.content = content;
        this.starScore = starScore;
    }
}
