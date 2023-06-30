package com.arton.backend.review.adapter.out.persistence.repository;


import com.arton.backend.artist.application.data.QCommonArtistDto;
import com.arton.backend.image.adapter.out.persistence.entity.QReviewImageEntity;
import com.arton.backend.performance.applicaiton.data.QPerformanceDetailQueryDslDtoV3;
import com.arton.backend.price.application.data.QPriceInfoDto;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.application.data.CommonReviewQueryDslDto;
import com.arton.backend.review.application.data.QCommonReviewQueryDslDto;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.arton.backend.artist.adapter.out.persistence.entity.QArtistEntity.artistEntity;
import static com.arton.backend.image.adapter.out.persistence.entity.QPerformanceImageEntity.performanceImageEntity;
import static com.arton.backend.image.adapter.out.persistence.entity.QReviewImageEntity.*;
import static com.arton.backend.image.adapter.out.persistence.entity.QUserImageEntity.userImageEntity;
import static com.arton.backend.performance.adapter.out.persistence.entity.QPerformanceEntity.performanceEntity;
import static com.arton.backend.performer.adapter.out.persistence.entity.QPerformerEntity.performerEntity;
import static com.arton.backend.price.adapter.out.persistence.entity.QPriceGradeEntity.priceGradeEntity;
import static com.arton.backend.review.adapter.out.persistence.entity.QReviewEntity.reviewEntity;
import static com.arton.backend.user.adapter.out.persistence.entity.QUserEntity.userEntity;
import static com.arton.backend.zzim.adapter.out.persistence.entity.QPerformanceZzimEntity.performanceZzimEntity;
import static com.querydsl.core.group.GroupBy.*;

@Repository
@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 해당 유저가 작성한 모든 댓글 정보를 가져온다.
     * @param userId
     * @return
     */
    @Override
    public List<CommonReviewQueryDslDto> getUserReviewList(long userId) {
        return jpaQueryFactory
                .from(reviewEntity)
                .leftJoin(performanceEntity).on(performanceEntity.eq(reviewEntity.performance))
                .leftJoin(userEntity).on(userEntity.eq(reviewEntity.user))
                .leftJoin(userImageEntity).on(userEntity.eq(userImageEntity.user))
                .leftJoin(reviewImageEntity).on(reviewEntity.eq(reviewImageEntity.review))
                .fetchJoin()
                .where(reviewEntity.user.id.eq(userId))
                .orderBy(reviewEntity.createdDate.asc())
                .transform(groupBy(reviewEntity.id).list(new QCommonReviewQueryDslDto(
                        reviewEntity.id,
                        reviewEntity.parent.id,
                        performanceEntity.id,
                        userEntity.id,
                        userImageEntity.imageUrl,
                        userEntity.nickname,
                        performanceEntity.title,
                        reviewEntity.starScore,
                        reviewEntity.createdDate,
                        reviewEntity.content,
                        set(reviewImageEntity.imageUrl),
                        reviewEntity.hit)));
    }

    @Override
    public List<ReviewEntity> getAllReviews(long performanceId) {
        return jpaQueryFactory.selectFrom(reviewEntity)
                .leftJoin(reviewEntity.parent)
                .fetchJoin()
                .where(reviewEntity.performance.id.eq(performanceId))
                .orderBy(reviewEntity.parent.id.asc().nullsFirst(), reviewEntity.createdDate.asc())
                .fetch();
    }

    @Override
    public List<ReviewEntity> getReviewChilds(long reviewId) {
        return jpaQueryFactory.selectFrom(reviewEntity)
                .leftJoin(reviewEntity.parent)
                .fetchJoin()
                .where(reviewEntity.id.eq(reviewId).or(reviewEntity.parent.id.eq(reviewId)))
                .orderBy(reviewEntity.parent.id.asc().nullsFirst(), reviewEntity.createdDate.asc())
                .fetch();
    }
}
