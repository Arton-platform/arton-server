package com.arton.backend.comment.application.service;

import com.arton.backend.comment.adapter.out.persistence.CommentMapper;
import com.arton.backend.comment.application.port.in.LoadCommentUseCase;
import com.arton.backend.comment.application.port.out.LoadCommentPort;
import com.arton.backend.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService implements LoadCommentUseCase {

//    private final LoadCommentPort loadCommentPort;
//    private final CommentMapper commentMapper;
//
//
    @Override
    public List<Comment> loadCommentByReviewId(long reviewId) {
//        return loadCommentPort.findAllCommentByReviewId(reviewId).map(comments -> comments
//                .stream()
//                .map(comment -> commentMapper.toDomain(comment))
//                .collect(Collectors.toList())
//        ).orElseGet(ArrayList::new);
        return null;
    }
}
