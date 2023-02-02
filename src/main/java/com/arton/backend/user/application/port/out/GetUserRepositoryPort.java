package com.arton.backend.user.application.port.out;

import com.arton.backend.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface GetUserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long id);
    Optional<User> findByNaverId(String id);
    Optional<User> findById(Long id);
    /** 패스워드 초기화를 위한 유저 찾기*/
    Optional<User> findUserForReset(String nickname, String email);
    List<User> findAll();
}
