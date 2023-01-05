package com.arton.backend.user.application.port.out;

import com.arton.backend.user.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long id);
    User save(User user);
}
