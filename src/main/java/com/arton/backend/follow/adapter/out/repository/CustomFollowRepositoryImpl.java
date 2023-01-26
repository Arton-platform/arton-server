package com.arton.backend.follow.adapter.out.repository;

import com.arton.backend.follow.applicaion.port.in.UserFollowSearchDto;
import com.arton.backend.follow.domain.SortCondition;
import com.arton.backend.user.adapter.out.repository.QUserEntity;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.arton.backend.follow.adapter.out.repository.QFollowEntity.*;
import static com.arton.backend.user.adapter.out.repository.QUserEntity.*;


@Repository
@RequiredArgsConstructor
public class CustomFollowRepositoryImpl implements CustomFollowRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserEntity> getFollowers(Long userId, UserFollowSearchDto userFollowSearchDto) {
        JPAQuery<UserEntity> where = queryFactory.selectFrom(userEntity)
                .innerJoin(followEntity)
                .on(userEntity.id.eq(followEntity.fromUser))
                .where(followEntity.toUser.eq(userId),
                        nickNameEq(userFollowSearchDto));

        if (StringUtils.hasText(userFollowSearchDto.getSort())) {
            SortCondition sortCondition = SortCondition.get(userFollowSearchDto.getSort().toUpperCase());
            if (!ObjectUtils.isEmpty(sortCondition) && sortCondition.equals(SortCondition.CREATEDDATE)) {
                where.orderBy(followEntity.createdDate.desc());
            }
        }
        return where.fetch();
    }

    private BooleanExpression nickNameEq(UserFollowSearchDto userFollowSearchDto) {
        return StringUtils.hasText(userFollowSearchDto.getNickname()) ? userEntity.nickname.eq(userFollowSearchDto.getNickname()) : null;
    }

    @Override
    public List<UserEntity> getFollowings(Long userId, UserFollowSearchDto userFollowSearchDto) {
        JPAQuery<UserEntity> where = queryFactory.selectFrom(userEntity)
                .innerJoin(followEntity)
                .on(userEntity.id.eq(followEntity.toUser))
                .where(followEntity.fromUser.eq(userId),
                        nickNameEq(userFollowSearchDto));

        if (StringUtils.hasText(userFollowSearchDto.getSort())) {
            SortCondition sortCondition = SortCondition.get(userFollowSearchDto.getSort().toUpperCase());
            if (!ObjectUtils.isEmpty(sortCondition) && sortCondition.equals(SortCondition.CREATEDDATE)) {
                where.orderBy(followEntity.createdDate.desc());
            }
        }
        return where.fetch();
    }
}
