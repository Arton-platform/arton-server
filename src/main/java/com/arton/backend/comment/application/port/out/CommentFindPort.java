package com.arton.backend.comment.application.port.out;

import com.arton.backend.comment.domain.Comment;

import java.util.Optional;

public interface CommentFindPort {
    Optional<Comment> findById(Long id);
}
