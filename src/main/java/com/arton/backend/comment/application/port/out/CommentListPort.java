package com.arton.backend.comment.application.port.out;

import com.arton.backend.comment.adapter.out.persistence.CommentEntity;
import com.arton.backend.review.domain.Review;

import java.util.List;
import java.util.Optional;

public interface CommentListPort {
    Optional<List<CommentEntity>> findAllByReviewOrderByCreatedDateDesc(Review review);
}
