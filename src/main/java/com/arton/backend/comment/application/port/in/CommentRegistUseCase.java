package com.arton.backend.comment.application.port.in;

import com.arton.backend.comment.domain.Comment;

public interface CommentRegistUseCase {
    Comment regist(Comment comment);
}
