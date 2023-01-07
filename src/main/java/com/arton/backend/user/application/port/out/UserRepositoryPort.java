package com.arton.backend.user.application.port.out;

import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long id);
    Optional<User> findByNaverId(String id);
    Optional<User> findById(Long id);
    /** 이메일 중복 여부 확인*/
    boolean checkEmailDup(String email);
    User save(User user);
}
