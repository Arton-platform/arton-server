package com.arton.backend.follow.applicaion.port.out;

import com.arton.backend.follow.adapter.out.persistence.entity.FollowFeedEntity;

public interface FollowFeedPort {
    void save(FollowFeedEntity entity);
}
