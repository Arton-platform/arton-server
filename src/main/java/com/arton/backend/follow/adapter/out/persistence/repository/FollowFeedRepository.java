package com.arton.backend.follow.adapter.out.persistence.repository;

import com.arton.backend.follow.adapter.out.persistence.entity.FollowFeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowFeedRepository extends JpaRepository<FollowFeedEntity, Long> {
}
