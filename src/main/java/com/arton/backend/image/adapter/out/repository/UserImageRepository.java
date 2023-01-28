package com.arton.backend.image.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImageEntity, Long> {
    Optional<UserImageEntity> findByUser_id(Long userId);
}
