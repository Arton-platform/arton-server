package com.arton.backend.comment.adapter.out.persistence;

import com.arton.backend.comment.application.port.out.CommentListPort;
import com.arton.backend.performance.domain.QPerformance;
import com.arton.backend.review.adapter.out.persistence.QReviewEntity;
import com.arton.backend.review.domain.Review;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Repository
public class CommentPersistenceAdapter implements CommentListPort {
    private final CommentRepository repository;
    private final EntityManager entityManager;
    @Override
    public Optional<List<CommentEntity>> findAllByReviewOrderByCreatedDateDesc(Review review) {
//        QPerformance qPerformance = QPerformance.performance;
//        QReviewEntity qReviewEntity = QReviewEntity.reviewEntity;
//        QCommentEntity qCommentEntity = QCommentEntity.commentEntity;
//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

//        return Optional.ofNullable(
//                queryFactory.selectFrom(qCommentEntity)
//                .where(qCommentEntity.review.eq(qReviewEntity).and(qCommentEntity.performance.eq(qPerformance)))
//                .orderBy(qCommentEntity.createdDate.desc())
//                .fetch()
//        );
        return null;
//        return repository.findAllByPerformanceAndReviewOrderByCreatedDateDesc(review);
    }
}
