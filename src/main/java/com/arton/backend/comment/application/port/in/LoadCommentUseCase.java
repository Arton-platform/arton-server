package com.arton.backend.comment.application.port.in;

import com.arton.backend.comment.domain.Comment;

import java.util.List;

public interface LoadCommentUseCase {
    List<Comment> loadCommentByReviewId(long reviewId);
}
