package com.arton.backend.follow.applicaion.port.out;

import com.arton.backend.follow.domain.Follow;

public interface FollowRegistRepositoryPort {
    Follow add(Follow follow);
}
