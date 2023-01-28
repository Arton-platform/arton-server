package com.arton.backend.image.application.port.out;

import com.arton.backend.image.domain.UserImage;

import java.util.Optional;

public interface UserImageRepositoryPort {
    Optional<UserImage> findUserImage(Long id);
}
