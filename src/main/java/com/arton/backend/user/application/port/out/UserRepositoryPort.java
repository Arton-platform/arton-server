package com.arton.backend.user.application.port.out;

import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByKakaoId(Long id);
    Optional<UserEntity> findByNaverId(String id);
    Optional<UserEntity> findById(Long id);
    /** 이메일 중복 여부 확인*/
    boolean checkEmailDup(String email);
    /** 패스워드 초기화를 위한 유저 찾기*/
    Optional<UserEntity> findUserForReset(String nickname, String email);
    UserEntity save(UserEntity user);
}
