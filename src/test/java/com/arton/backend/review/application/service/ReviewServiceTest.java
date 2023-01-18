package com.arton.backend.review.application.service;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    ReviewService service;

    @Test
    void regist() {
        UserEntity user = UserEntity.builder()
                .email("thdrmsqhd@gmail.com")
                .nickname("thdrmsqhd")
                .id(1L)
                .build();

        PerformanceEntity performance = PerformanceEntity.builder()
                .id(1L)
                .title("title")
                .build();

        Review review = Review.builder()
                .reviewId(1L)
                .content("content")
                .performance(performance)
                .user(user)
                .build();

        service.regist(review);
    }
}