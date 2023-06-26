package com.arton.backend.comment.adapter.in.web;

import com.arton.backend.comment.application.port.in.CommentListUseCase;
import com.arton.backend.comment.application.port.in.CommentRegistUseCase;
import com.arton.backend.comment.domain.Comment;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.application.data.MyPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "공연 리뷰의 댓글보기", description = "해당 공연의 특정 리뷰에 대한 댓글을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "코멘트 반환 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema( implementation = Comment.class))))})
    @GetMapping("/list/{performanceId}/{reviewId}")
    public ResponseEntity<ResponseData<List<Comment>>> commentList(@PathVariable("performanceId") Long performanceId, @PathVariable("reviewId") Long reviewId){
        PerformanceEntity performance = PerformanceEntity.builder()
                .id(performanceId)
                .build();

        Review review = Review.builder()
                .id(reviewId)
                .performanceId(performance.getId())
                .build();
        ResponseData<List<Comment>> response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                commentListUseCase.commentList(review)
        );

        return ResponseEntity.ok().body(response);
    }
    @Operation(summary = "공연 리뷰 댓글 등록", description = "해당 공연의 특정 리뷰에 대한 댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "코멘트 등록 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema( implementation = Comment.class))))})
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
