package com.arton.backend.comment.application.port.out;

import com.arton.backend.comment.adapter.out.persistence.CommentEntity;
import com.arton.backend.comment.domain.Comment;

public interface CommentRegistPort {
    void regist(CommentEntity comment);
}
