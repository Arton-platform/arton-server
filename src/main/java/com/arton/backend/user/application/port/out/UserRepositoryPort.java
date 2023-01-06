package com.arton.backend.user.application.port.out;

import com.arton.backend.user.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long id);
    Optional<User> findByNaverId(String id);
    User save(User user);
}
