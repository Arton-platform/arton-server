package com.arton.backend.image.adapter.out.persistence.repository;

import com.arton.backend.image.adapter.out.persistence.entity.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Long> {
}
