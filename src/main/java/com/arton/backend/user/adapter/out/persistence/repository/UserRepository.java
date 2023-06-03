package com.arton.backend.user.adapter.out.persistence.repository;

import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.domain.SignupType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {
    List<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPlatformIdAndSignUpType(String platformId, SignupType signupType);
    List<UserEntity> findAllByOrderByIdDesc();
}
