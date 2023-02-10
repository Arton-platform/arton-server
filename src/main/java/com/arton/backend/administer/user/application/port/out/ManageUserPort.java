package com.arton.backend.administer.user.application.port.out;

import com.arton.backend.administer.user.adapter.in.web.dto.UserUpdateByAdminDto;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ManageUserPort {
    Optional<List<UserEntity>> allUser();

    Optional<UserEntity> userDetail(Long id);

    void saveUser(UserEntity entity);

    void updateUserByAdmin(UserUpdateByAdminDto userUpdateByAdminDto);

    void deleteUserByAdmin(UserEntity entity);
}
