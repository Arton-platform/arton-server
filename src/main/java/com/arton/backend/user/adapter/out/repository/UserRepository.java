package com.arton.backend.user.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {
    Optional<UserEntity> findByKakaoId(Long kakaoId);
    List<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByNaverId(String naverId);
}
