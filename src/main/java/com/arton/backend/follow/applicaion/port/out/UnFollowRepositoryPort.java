package com.arton.backend.follow.applicaion.port.out;

import com.arton.backend.follow.applicaion.port.in.UserFollowSearchDto;
import com.arton.backend.follow.domain.Follow;
import com.arton.backend.user.domain.User;

import java.util.List;

public interface UnFollowRepositoryPort {
    void delete(Follow follow);
}
