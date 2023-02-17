package com.arton.backend.administer.admin.application.port.out;

import java.util.List;
import java.util.Optional;

import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;

public interface AdminUserPort {

    void regist(UserEntity entity);

    Optional<UserEntity> finadAdmin(Long id);

    Optional<List<UserEntity>> fiandAll();

    void delete(UserEntity entity);
    
}
