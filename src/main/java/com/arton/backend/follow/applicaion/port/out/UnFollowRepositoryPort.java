package com.arton.backend.follow.applicaion.port.out;

import com.arton.backend.follow.domain.Follow;

public interface UnFollowRepositoryPort {
    void delete(Follow follow);
}
