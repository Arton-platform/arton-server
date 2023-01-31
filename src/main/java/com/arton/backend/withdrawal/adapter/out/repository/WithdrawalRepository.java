package com.arton.backend.withdrawal.adapter.out.repository;

import com.arton.backend.user.adapter.out.repository.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WithdrawalRepository extends JpaRepository<WithdrawalEntity, Long> {
    Optional<WithdrawalEntity> findByUser(UserEntity user);
    Optional<WithdrawalEntity> findByUser_Id(Long userId);
}
