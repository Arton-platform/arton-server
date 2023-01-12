package com.arton.backend.comment.adapter.out.persistence;

import com.arton.backend.comment.domain.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toDomain(CommentEntity entity) {
        return Comment.builder()
                .commentId(entity.getCommentId())
                .review(entity.getReview())
                .content(entity.getContent())
                .hit(entity.getHit())
                .image(entity.getImage())
                .user(entity.getUser())
                .createdDate(entity.getCreatedDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    public CommentEntity toEntity(Comment comment){
        return CommentEntity.builder()
                .commentId(comment.getCommentId())
                .review(comment.getReview())
                .content(comment.getContent())
                .hit(comment.getHit())
                .image(comment.getImage())
                .user(comment.getUser())
                .createdDate(comment.getCreatedDate())
                .updateDate(comment.getUpdateDate())
                .build();
    }
}
