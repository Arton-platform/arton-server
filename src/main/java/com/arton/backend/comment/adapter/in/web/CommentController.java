package com.arton.backend.comment.adapter.in.web;

import com.arton.backend.comment.application.port.in.CommentListUseCase;
import com.arton.backend.comment.application.port.in.CommentRegistUseCase;
import com.arton.backend.comment.domain.Comment;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentListUseCase commentListUseCase;
    private final CommentRegistUseCase commentRegistUseCase;

    @GetMapping("/list/{performanceId}/{reviewId}")
    public ResponseEntity<ResponseData<List<Comment>>> commentList(@PathVariable("performanceId") Long performanceId, @PathVariable("reviewId") Long reviewId){
        PerformanceEntity performance = PerformanceEntity.builder()
                .id(performanceId)
                .build();

        Review review = Review.builder()
                .reviewId(reviewId)
                .performance(performance)
                .build();
        ResponseData<List<Comment>> response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                commentListUseCase.commentList(review)
        );

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/regist")
    public ResponseEntity<ResponseData<Comment>> regist(@RequestBody Comment comment){
        ResponseData<Comment> response = new ResponseData<>(
            "SUCCESS",
            HttpStatus.OK.value(),
            commentRegistUseCase.regist(comment)
        );
        return ResponseEntity.ok().body(response);
    }
}
