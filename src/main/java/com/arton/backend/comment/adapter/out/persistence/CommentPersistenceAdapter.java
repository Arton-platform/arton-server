package com.arton.backend.comment.adapter.out.persistence;

import com.arton.backend.comment.application.port.out.LoadCommentPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CommentPersistenceAdapter implements LoadCommentPort {

    private final CommentRepository repository;

    @Override
    public Optional<List<CommentEntity>> findAllCommentByReviewId(long reviewId) {
        return repository.findAllCommentByReviewId(reviewId);
    }
}
