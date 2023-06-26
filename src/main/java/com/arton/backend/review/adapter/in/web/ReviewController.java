package com.arton.backend.review.adapter.in.web;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import com.arton.backend.review.application.data.ReviewCreateDto;
import com.arton.backend.review.application.data.ReviewDto;
import com.arton.backend.review.application.port.in.ReviewListUseCase;
import com.arton.backend.review.application.port.in.ReviewRegistUseCase;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.application.data.MyPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewListUseCase reviewListUseCase;
    private final ReviewRegistUseCase reviewRegistUseCase;

    @Operation(summary = "공연 리뷰 페이지", description = "공연의 리뷰 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "타유저 정보 반환 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Review.class))))})
    @GetMapping("/list/{id}")
    public ResponseEntity<ResponseData<List<ReviewDto>>> reviewList(@PathVariable(value = "id" ,required = true) Long id){
        ResponseData<List<ReviewDto>> response = new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                reviewListUseCase.reviewList(id)
        );
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "공연 리뷰 등록", description = "공연 리뷰를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 등록 성공",
                    content = @Content(schema = @Schema(implementation = Review.class)))})
    @PostMapping("/regist")
    public ResponseEntity<CommonResponse> regist(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReviewCreateDto reviewCreateDto){
        long userId = Long.parseLong(userDetails.getUsername());
        // enroll
        reviewRegistUseCase.regist(userId, reviewCreateDto);
        return ResponseEntity.ok().body(CommonResponse.builder().status(200).message("리뷰를 성공적으로 등록하였습니다.").build());
    }
}
