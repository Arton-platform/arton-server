package com.arton.backend.administer.admin.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arton.backend.administer.admin.application.port.in.AdminUserUseCase;
import com.arton.backend.administer.admin.application.port.out.AdminUserPort;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import com.arton.backend.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserService implements AdminUserUseCase {

    private final AdminUserPort adminUserPort;

    @Override
    public void regist(User user) {
        log.info("[regist] : {}", user.toString());

        try {
            adminUserPort.regist(
                    UserMapper.toEntity(user));
        } catch (Exception e) {
            new CustomException(ErrorCode.REGIST_ERROR.getMessage(), ErrorCode.REGIST_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    public User findAdmin(Long id) {
        log.info("[findAdmin] : {}", id);
        return UserMapper.toDomain(
                adminUserPort.findAdmin(id).orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public List<User> findAll() {
        log.info("[findAll] : {}", "findAll`");
        return adminUserPort.findAll().orElseGet(ArrayList::new).stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(User user) {
        log.info("[update] : {}", user.toString());
        UserEntity origianl = adminUserPort.findAdmin(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        UserEntity update = UserMapper.toEntity(user);

        origianl.setSelfDescription(update.getSelfDescription())
                .setPassword(update.getPassword())
                .setImage(update.getUserImage());
    }

    @Override
    public void delete(User user) {
        log.info("[delete] : {}", user.toString());
        // 삭제하기 전에 관리자의 숫자를 측정한다.
        Long adminCount = adminUserPort.findAll().stream().count();
        if (adminCount > 1) {
            adminUserPort.delete(
                    UserMapper.toEntity(user));
        } else {
            throw new CustomException("관리자는 최소 1명이상 존재해야 합니다.", ErrorCode.DELETE_ERROR);
        }
    }

}
