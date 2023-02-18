package com.arton.backend.administer.review.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arton.backend.administer.review.application.port.in.AdminReviewUseCase;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.review.domain.Review;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class AdminReviewController {

    private final AdminReviewUseCase adminReviewUseCase;

    // 전체 조회
    @GetMapping("/findReview")
    public ResponseData<List<Review>> findReview() {
        return new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                adminReviewUseCase.findReview());
    }

    // 단건 조회
    @GetMapping("/findReview/{id}")
    public ResponseData<Review> findReviewById(@PathVariable("id") Long id) {
        return new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                adminReviewUseCase.findReviewById(id));
    }

    // 삭제 조치
    @DeleteMapping("/delete-review")
    public CommonResponse deleteReview(Review review) {
        adminReviewUseCase.deleteReview(review);
        return CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
    }

}