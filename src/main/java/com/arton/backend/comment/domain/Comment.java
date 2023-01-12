package com.arton.backend.comment.domain;

import com.arton.backend.common.entity.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends Board {

    private long commentId;
    private ReviewEntity review;
    private Performance performance;
    private String comment;

    @Builder
    public Comment(long commentId, ReviewEntity review, Performance performance, String comment, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate){
        super(user,hit,image,createdDate,updateDate);
        this.commentId = commentId;
        this.review = review;
        this.performance = performance;
        this.comment = comment;
    }
}
