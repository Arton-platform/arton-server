package com.arton.backend.administer.admin.application.port.in;

import java.util.List;

import com.arton.backend.administer.admin.application.data.AdminSignupDTO;
import com.arton.backend.auth.application.data.LoginRequestDto;
import com.arton.backend.auth.application.data.TokenDto;
import com.arton.backend.user.domain.User;

public interface AdminUserUseCase {

    void regist(User user);
    void registV2(AdminSignupDTO adminSignupDTO);
    TokenDto login(LoginRequestDto loginRequestDto);
    User findAdmin(Long id);

    List<User> findAll();

    void update(User user);

    void delete(User user);

}
