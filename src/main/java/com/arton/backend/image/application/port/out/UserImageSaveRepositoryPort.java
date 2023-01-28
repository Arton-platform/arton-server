package com.arton.backend.image.application.port.out;

import com.arton.backend.image.domain.UserImage;

public interface UserImageSaveRepositoryPort {
    UserImage save(UserImage userImage);
}
