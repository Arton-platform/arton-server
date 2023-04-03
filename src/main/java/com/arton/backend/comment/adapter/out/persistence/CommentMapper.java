package com.arton.backend.comment.adapter.out.persistence;

import com.arton.backend.comment.domain.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toDomain(CommentEntity entity){
        return Comment.builder()
                .commentId(entity.getCommentId())
                .hit(entity.getHit())
                .performance(entity.getPerformance())
                .user(entity.getUser())
                .createdDate(entity.getCreatedDate())
                .image(entity.getImage())
                .updateDate(entity.getUpdateDate())
                .review(entity.getReview())
                .comment(entity.getComment())
                .build();
    }

    public CommentEntity toEntity(Comment entity){
        return CommentEntity.builder()
                .commentId(entity.getCommentId())
                .hit(entity.getHit())
                .performance(entity.getPerformance())
                .user(entity.getUser())
                .createdDate(entity.getCreatedDate())
                .image(entity.getImage())
                .updateDate(entity.getUpdateDate())
                .review(entity.getReview())
                .comment(entity.getComment())
                .build();
    }

}
