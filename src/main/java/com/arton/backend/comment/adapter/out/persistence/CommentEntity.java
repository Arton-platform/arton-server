package com.arton.backend.comment.adapter.out.persistence;

import com.arton.backend.common.entity.Board;
import com.arton.backend.board.adapter.out.persistence.review.Review;
import com.arton.backend.image.domain.Image;
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
public class CommentEntity extends Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;
    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    private String content;

    @Builder
    public CommentEntity(long commentId, Review review, String content, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.commentId = commentId;
        this.review = review;
        this.content = content;
    }
}
