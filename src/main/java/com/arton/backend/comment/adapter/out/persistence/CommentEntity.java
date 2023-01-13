package com.arton.backend.comment.adapter.out.persistence;

import com.arton.backend.infra.shared.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;
import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "comment")
public class CommentEntity extends Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;
    @ManyToOne
    @JoinColumn(name = "reviewId")
    private ReviewEntity review;
    @ManyToOne
    @JoinColumn(name = "performanceId")
    private Performance performance;
    private String comment;

    @Builder
    public CommentEntity(long commentId, ReviewEntity review, Performance performance, String comment, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate){
        super(user,hit,image,createdDate,updateDate);
        this.commentId = commentId;
        this.review = review;
        this.performance = performance;
        this.comment = comment;
    }
}
