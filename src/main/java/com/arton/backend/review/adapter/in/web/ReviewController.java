package com.arton.backend.review.adapter.in.web;

import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.review.application.port.in.ReviewListUseCase;
import com.arton.backend.review.application.port.in.ReviewRegistUseCase;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewListUseCase reviewListUseCase;
    private final ReviewRegistUseCase reviewRegistUseCase;
    @GetMapping("/list/{id}")
    public ResponseEntity<ResponseData<List<Review>>> reviewList(@PathVariable(value = "id" ,required = true) Long id){
        ResponseData<List<Review>> response = new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                reviewListUseCase.reviewList(id)
        );
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/regist")
    public ResponseEntity<ResponseData<Review>> regist(@RequestBody Review review){
        ResponseData<Review> response = new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(), reviewRegistUseCase.regist(review)
        );
        return ResponseEntity.ok().body(response);
    }
}
