package com.arton.backend.user.adapter.out.repository;

import com.arton.backend.user.domain.SignupType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

import static com.arton.backend.user.adapter.out.repository.QUserEntity.*;


@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserEntity> getValidUserByEmailAndSignupType(String email, SignupType signupType) {
        UserEntity user = queryFactory.selectFrom(userEntity)
                .where(userEntity.email.eq(email),
                        userEntity.signupType.eq(signupType),
                        userEntity.userStatus.eq(true)).fetchOne();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<UserEntity> getUserForResetPassword(String nickname, String email) {
        UserEntity user = queryFactory.selectFrom(userEntity)
                .where(userEntity.nickname.eq(nickname),
                        userEntity.email.eq(email),
                        userEntity.signupType.eq(SignupType.ARTON),
                        userEntity.userStatus.eq(true)).fetchOne();
        return Optional.ofNullable(user);
    }

    /**
     * 중복 true
     * 유니크 false
     * @param email
     * @return
     */
    @Override
    public boolean checkEmailDup(String email) {
        return !ObjectUtils.isEmpty(queryFactory.selectFrom(userEntity)
                .where(userEntity.email.eq(email),
                        userEntity.signupType.eq(SignupType.ARTON),
                        userEntity.userStatus.eq(true)).fetchOne());
    }
}
