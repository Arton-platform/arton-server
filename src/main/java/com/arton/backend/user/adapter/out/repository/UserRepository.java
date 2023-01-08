package com.arton.backend.user.adapter.out.repository;

import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(Long kakaoId);
    Optional<User> findByEmail(String email);
    Optional<User> findByNaverId(String naverId);
    Optional<User> findByEmailAndSignupType(String email, SignupType signupType);
    Optional<User> findByNicknameAndEmailAndSignupType(String nickname, String email, SignupType signupType);
    boolean existsByEmailAndSignupType(String email, SignupType signupType);
}
