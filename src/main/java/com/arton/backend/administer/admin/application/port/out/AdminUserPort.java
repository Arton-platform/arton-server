package com.arton.backend.administer.admin.application.port.out;

import java.util.List;
import java.util.Optional;

import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;

public interface AdminUserPort {

    void regist(UserEntity entity);

    Optional<UserEntity> findAdmin(Long id);

    Optional<List<UserEntity>> findAll();

    void delete(UserEntity entity);

}
