package com.arton.backend.review.adapter.in.web;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import com.arton.backend.review.application.data.CommonReviewDto;
import com.arton.backend.review.application.data.ReviewCreateDto;
import com.arton.backend.review.application.data.ReviewEditDto;
import com.arton.backend.review.application.data.ReviewForPerformanceDetailDto;
import com.arton.backend.review.application.port.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewListUseCase reviewListUseCase;
    private final ReviewRegistUseCase reviewRegistUseCase;
    private final ReviewDeleteUseCase reviewDeleteUseCase;
    private final ReviewEditUseCase reviewEditUseCase;
    private final ReviewHitRemoveUseCase reviewHitRemoveUseCase;
    private final ReviewHitAddUseCase reviewHitAddUseCase;

    @Operation(summary = "특정 리뷰 대댓글", description = "특정 리뷰의 대댓글 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 대댓글 반환 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommonReviewDto.class))))})
    @GetMapping("/{id}/child")
    public ResponseEntity<ResponseData<List<CommonReviewDto>>> getReviewChilds(@PathVariable(value = "id" ,required = true) Long id){
        ResponseData<List<CommonReviewDto>> response = new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                reviewListUseCase.getReviewChilds(id)
        );
        return ResponseEntity.ok().body(response);
    }

    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "공연 리뷰 등록", description = "공연 리뷰를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 등록 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class)))})
    @PostMapping(value = "/regist", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> regist(@AuthenticationPrincipal UserDetails userDetails, @RequestPart(required = true, name = "review") @Valid ReviewCreateDto review, @RequestPart(required = false, name = "image") List<MultipartFile> multipartFile){
        log.debug("Review regist request body {}", review);
        long userId = Long.parseLong(userDetails.getUsername());
        // enroll
//        reviewRegistUseCase.regist(userId, review);
        reviewRegistUseCase.regist(userId, review, multipartFile);
        return ResponseEntity.ok().body(CommonResponse.builder().status(200).message("리뷰를 성공적으로 등록하였습니다.").build());
    }

    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "공연 리뷰 삭제", description = "공연 리뷰를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "리뷰 삭제 성공"),
    @ApiResponse(responseCode = "404", description = "리뷰 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") long id) {
        long userId = Long.parseLong(userDetails.getUsername());
        reviewDeleteUseCase.delete(userId, id);
        return ResponseEntity.noContent().build();
    }

    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "공연 리뷰 수정", description = "공연 리뷰를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "리뷰 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping
    public ResponseEntity<CommonResponse> edit(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid ReviewEditDto reviewEditDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        reviewEditUseCase.edit(userId, reviewEditDto);
        return ResponseEntity.ok().body(CommonResponse.builder().status(200).message("성공적으로 리뷰를 수정하였습니다.").build());
    }

    @Operation(summary = "공연 리뷰 정보 반환", description = "해당 공연의 리뷰 정보(대댓글 포함)를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 반환 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReviewForPerformanceDetailDto.class))))})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ReviewForPerformanceDetailDto>> getSpecificReview(@PathVariable(value = "id" ,required = true) Long id){
        ResponseData<ReviewForPerformanceDetailDto> response = new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                reviewListUseCase.reviewList(id)
        );
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "리뷰 좋아요 누르기", description = "해당 리뷰를 좋아요 표시합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "리뷰 좋아요 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "리뷰 존재하지 않음",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping("/hit/{id}")
    public ResponseEntity addHit(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(value = "id" ,required = true) Long id){
        long userId = Long.parseLong(userDetails.getUsername());
        reviewHitAddUseCase.addHit(userId, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "리뷰 좋아요 해제", description = "해당 리뷰 좋아요를 해제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "리뷰 좋아요 해제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "리뷰 존재하지 않음",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @DeleteMapping("/hit/{id}")
    public ResponseEntity removeHit(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(value = "id" ,required = true) Long id){
        long userId = Long.parseLong(userDetails.getUsername());
        reviewHitRemoveUseCase.removeHit(userId, id);
        return ResponseEntity.noContent().build();
    }


}
