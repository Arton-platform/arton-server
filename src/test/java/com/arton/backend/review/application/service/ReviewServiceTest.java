package com.arton.backend.review.application.service;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceRepository;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.adapter.out.repository.UserRepository;
import com.arton.backend.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    ReviewService service;

    @Test
    void reviewList(){
        this.regist();
        List<Review> reviewList = service.reviewList(1L);

        assertEquals(reviewList.size() != 0, true);
    }

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

    @Commit
    @Test
    void count() {
        UserEntity user = UserEntity.builder()
                .email("thdrmsqhd@gmail.com")
                .nickname("thdrmsqhd")
                .id(1L)
                .build();

        PerformanceEntity performance = PerformanceEntity.builder()
                .id(1L)
                .title("title")
                .build();

        PerformanceEntity performance2 = PerformanceEntity.builder()
                .id(2L)
                .title("title")
                .build();

        Review review = Review.builder()
                .reviewId(1L)
                .content("content")
                .performance(performance)
                .user(user)
                .build();

        service.regist(review);

        Review review2 = Review.builder()
                .reviewId(2L)
                .content("caontent")
                .performance(performance2)
                .user(user)
                .build();

        service.regist(review2);

        Long reviewCount = service.reviewCount(user.getId());
        assertEquals(reviewCount, 2);
    }
}