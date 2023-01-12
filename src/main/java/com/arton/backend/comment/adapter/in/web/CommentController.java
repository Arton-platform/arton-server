package com.arton.backend.comment.adapter.in.web;

import com.arton.backend.board.adapter.out.persistence.review.Review;
import com.arton.backend.comment.application.port.in.LoadCommentUseCase;
import com.arton.backend.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final LoadCommentUseCase loadCommentUseCase;

    @GetMapping("/load/{reviewId}")
    public List<Comment> loadComment(@PathVariable("reviewId") long reviewId){
        return loadCommentUseCase.loadCommentByReviewId(reviewId);
    }
}
