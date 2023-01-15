package com.arton.backend.comment.adapter.in.web;

import com.arton.backend.comment.application.port.in.CommentListUseCase;
import com.arton.backend.comment.domain.Comment;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentListUseCase commentListUseCase;

    @GetMapping("/list/{performanceId}/{reviewId}")
    public List<Comment> commentList(@PathVariable("performanceId") Long performanceId, @PathVariable("reviewId") Long reviewId){
        PerformanceEntity performance = PerformanceEntity.builder()
                .id(performanceId)
                .build();

        Review review = Review.builder()
                .reviewId(reviewId)
                .performance(performance)
                .build();

        return commentListUseCase.commentList(review);
    }
}
