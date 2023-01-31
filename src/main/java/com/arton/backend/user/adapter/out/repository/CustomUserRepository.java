package com.arton.backend.user.adapter.out.repository;

import com.arton.backend.user.domain.SignupType;

import java.util.Optional;

public interface CustomUserRepository {
    Optional<UserEntity> getValidUserByEmailAndSignupType(String email, SignupType signupType);
    Optional<UserEntity> getUserForResetPassword(String nickname, String email);
    boolean checkEmailDup(String email);
}
