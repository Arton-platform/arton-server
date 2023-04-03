package com.arton.backend.comment.domain;

import com.arton.backend.infra.shared.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
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
    private PerformanceEntity performance;
    private String comment;

    @Builder
    public Comment(long commentId, ReviewEntity review, PerformanceEntity performance, String comment, UserEntity user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate){
        super(user,hit,image,createdDate,updateDate);
        this.commentId = commentId;
        this.review = review;
        this.performance = performance;
        this.comment = comment;
    }
}
