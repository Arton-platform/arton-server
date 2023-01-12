package com.arton.backend.comment.domain;

import com.arton.backend.board.adapter.out.persistence.review.Review;
import com.arton.backend.common.entity.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment extends Board {

    private long commentId;
    private Review review;
    private String content;

    @Builder
    public Comment(long commentId, Review review, String content, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.commentId = commentId;
        this.review = review;
        this.content = content;
    }
}
