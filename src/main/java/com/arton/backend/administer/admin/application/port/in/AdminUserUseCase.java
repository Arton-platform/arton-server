package com.arton.backend.administer.admin.application.port.in;

import java.util.List;

import com.arton.backend.user.domain.User;

public interface AdminUserUseCase {

    void regist(User user);

    User findAdmin(Long id);

    List<User> findAll();

    void update(User user);

    void delete(User user);

}
