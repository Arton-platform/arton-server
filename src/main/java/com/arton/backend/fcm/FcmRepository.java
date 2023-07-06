package com.arton.backend.fcm;

import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmRepository extends JpaRepository<FcmEntity, Long> {
    Optional<FcmEntity> findByUser(UserEntity user);
    Optional<FcmEntity> findByUserId(Long userId);
}
