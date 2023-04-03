package com.arton.backend.comment.application.service;

import com.arton.backend.comment.adapter.out.persistence.CommentEntity;
import com.arton.backend.comment.application.port.in.CommentListUseCase;
import com.arton.backend.comment.application.port.in.CommentRegistUseCase;
import com.arton.backend.comment.application.port.out.CommentListPort;
import com.arton.backend.comment.adapter.out.persistence.CommentMapper;
import com.arton.backend.comment.application.port.out.CommentRegistPort;
import com.arton.backend.comment.domain.Comment;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentListUseCase, CommentRegistUseCase {

    private final CommentListPort commentListPort;
    private final CommentRegistPort commentRegistPort;
    private final CommentMapper commentMapper;
    public List<Comment> commentList(Review review){
        return commentListPort.findAllByReviewOrderByCreatedDateDesc(review).map(commentEntities -> commentEntities
                .stream()
                .map(commentEntity -> commentMapper.toDomain(commentEntity))
                .collect(Collectors.toList())
        ).orElseGet(ArrayList::new);
    }

    @Override
    public Comment regist(Comment comment) {
        CommentEntity entity = commentMapper.toEntity(comment);
        commentRegistPort.regist(entity);
        return comment;
    }
}
