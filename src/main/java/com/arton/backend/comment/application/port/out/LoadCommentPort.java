package com.arton.backend.comment.application.port.out;

import com.arton.backend.comment.adapter.out.persistence.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface LoadCommentPort {
    Optional<List<CommentEntity>> findAllCommentByReviewId(long reviewId);
}
