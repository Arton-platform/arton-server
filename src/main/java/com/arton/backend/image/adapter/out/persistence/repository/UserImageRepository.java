package com.arton.backend.image.adapter.out.persistence.repository;

import com.arton.backend.image.adapter.out.persistence.entity.UserImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImageEntity, Long> {
    Optional<UserImageEntity> findByUser_id(Long userId);
    void deleteByUser_Id(Long userId);
}
