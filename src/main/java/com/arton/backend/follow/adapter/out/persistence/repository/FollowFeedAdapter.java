package com.arton.backend.follow.adapter.out.persistence.repository;

import com.arton.backend.follow.adapter.out.persistence.entity.FollowFeedEntity;
import com.arton.backend.follow.applicaion.port.out.FollowFeedPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowFeedAdapter implements FollowFeedPort {
    private final FollowFeedRepository repository;

    @Override
    public void save(FollowFeedEntity entity) {
        repository.save(entity);
    }
}
