package com.arton.backend.administer.admin.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;

public interface AdaminUserRepository extends JpaRepository<UserEntity, Long> {

}
