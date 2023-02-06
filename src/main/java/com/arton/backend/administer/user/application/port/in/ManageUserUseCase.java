package com.arton.backend.administer.user.application.port.in;

import com.arton.backend.administer.user.adapter.in.web.dto.UserDeleteByAdminDto;
import com.arton.backend.administer.user.adapter.in.web.dto.UserRegisterByAdminDto;
import com.arton.backend.administer.user.adapter.in.web.dto.UserUpdateByAdminDto;
import com.arton.backend.user.domain.User;

import java.util.List;

public interface ManageUserUseCase {
    List<User> allUser();

    User userDetail(Long id);

    void registerUserByAdmin(UserRegisterByAdminDto userRegisterByAdminDto);

    void updateUserByAdmin(UserUpdateByAdminDto userUpdateByAdminDto);

    void deleteUserByAdmin(UserDeleteByAdminDto userDeleteByAdminDto);
}
