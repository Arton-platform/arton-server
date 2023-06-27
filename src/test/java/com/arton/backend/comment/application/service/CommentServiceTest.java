package com.arton.backend.comment.application.service;

import com.arton.backend.comment.adapter.out.persistence.CommentEntity;
import com.arton.backend.comment.adapter.out.persistence.CommentRepository;
import com.arton.backend.comment.domain.Comment;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.adapter.out.persistence.repository.PerformanceRepository;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.adapter.out.persistence.repository.ReviewRepository;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService service;
    @Autowired
    CommentRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PerformanceRepository performanceRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    void registParameter(){
        UserEntity u = UserEntity.builder()
                .id(1L)
                .email("thdrmsqhd@gmail.com")
                .nickname("thdrsmqhd")
                .build();
        userRepository.save(u);

        PerformanceEntity p = PerformanceEntity.builder()
                .id(1L)
                .title("title")
                .description("desc")
                .build();
        performanceRepository.save(p);

        ReviewEntity r = ReviewEntity.builder()
                .id(1L)
                .content("content")
                .performance(p)
                .build();
        reviewRepository.save(r);
    }

    @Test
    void commentList() {
    }

    @Test
    void regist() {
        PerformanceEntity p = PerformanceEntity.builder()
                .id(1L)
                .title("title")
                .description("desc")
                .build();

        ReviewEntity r = ReviewEntity.builder()
                .id(1L)
                .content("content")
                .performance(p)
                .build();

        UserEntity u = UserEntity.builder()
                .id(1L)
                .email("thdrmsqhd@gmail.com")
                .nickname("thdrsmqhd")
                .build();

        Comment entity = Comment.builder()
                .performance(p)
                .review(r)
                .commentId(1L)
                .comment("comment")
                .user(u)
                .build();



        service.regist(entity);

        Optional<CommentEntity> optionalCommentEntity = repository.findById(entity.getCommentId());
        CommentEntity commentEntity = optionalCommentEntity.get();

        assertEquals(entity.getCommentId(), commentEntity.getCommentId());
    }
}