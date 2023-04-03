package com.arton.backend.comment.application.port.in;

import com.arton.backend.comment.domain.Comment;
import com.arton.backend.review.domain.Review;

import java.util.List;

public interface CommentListUseCase {

    List<Comment> commentList(Review review);
}
