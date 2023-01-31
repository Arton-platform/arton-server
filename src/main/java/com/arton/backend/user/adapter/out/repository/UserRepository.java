package com.arton.backend.user.adapter.out.repository;

import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {
    Optional<UserEntity> findByKakaoId(Long kakaoId);
    List<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByNaverId(String naverId);
    Optional<UserEntity> findByEmailAndSignupType(String email, SignupType signupType);
    Optional<UserEntity> findByNicknameAndEmailAndSignupType(String nickname, String email, SignupType signupType);
    boolean existsByEmailAndSignupType(String email, SignupType signupType);
}
