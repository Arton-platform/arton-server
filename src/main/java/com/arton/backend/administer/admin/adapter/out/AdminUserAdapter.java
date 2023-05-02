package com.arton.backend.administer.admin.adapter.out;

import java.util.List;
import java.util.Optional;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.stereotype.Repository;

import com.arton.backend.administer.admin.application.port.out.AdminUserPort;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.domain.UserRole;

import lombok.RequiredArgsConstructor;

import static com.arton.backend.user.adapter.out.persistence.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class AdminUserAdapter implements AdminUserPort {

    private final AdaminUserRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public void regist(UserEntity entity) {
        repository.save(entity);
    }

    @Override
    public Optional<UserEntity> findAdmin(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<List<UserEntity>> findAll() {
        return Optional.ofNullable(
                queryFactory.selectFrom(userEntity)
                        .where(userEntity.auth.eq(UserRole.ROLE_ADMIN))
                        .fetch());
    }

    @Override
    public void delete(UserEntity entity) {
        repository.delete(entity);
    }

}
