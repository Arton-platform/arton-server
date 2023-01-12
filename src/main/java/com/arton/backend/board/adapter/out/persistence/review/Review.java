package com.arton.backend.board.adapter.out.persistence.review;

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

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @ManyToOne
    @JoinColumn(name = "performanceId")
    private Performance performance;
    private String content;
    private float starScore;

    @Builder
    public Review(Performance performance, String content, float starScore, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.performance = performance;
        this.content = content;
        this.starScore = starScore;
    }
}
