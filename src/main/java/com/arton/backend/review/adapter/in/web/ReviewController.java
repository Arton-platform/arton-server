package com.arton.backend.review.adapter.in.web;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.application.port.in.ReviewListUseCase;
import com.arton.backend.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewListUseCase reviewListUseCase;
    @GetMapping("/list/{id}")
    public List<Review> reviewList(@PathVariable(value = "id" ,required = true) Long id){
        Performance performance = Performance.builder()
                .performanceId(id)
                .build();
        return reviewListUseCase.reviewList(performance);
    }
}
