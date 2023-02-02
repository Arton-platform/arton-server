package com.arton.backend.user.application.port.out;

import com.arton.backend.user.domain.User;

public interface UserSaveRepositoryPort {
    User save(User user);
}
