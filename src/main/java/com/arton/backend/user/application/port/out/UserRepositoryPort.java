package com.arton.backend.user.application.port.out;

import com.arton.backend.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long id);
    Optional<User> findByNaverId(String id);
    Optional<User> findById(Long id);
    /** 이메일 중복 여부 확인*/
    boolean checkEmailDup(String email);
    /** 패스워드 초기화를 위한 유저 찾기*/
    Optional<User> findUserForReset(String nickname, String email);
    User save(User user);
    List<User> findAll();
}
