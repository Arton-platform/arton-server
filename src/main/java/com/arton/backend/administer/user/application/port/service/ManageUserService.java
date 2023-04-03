package com.arton.backend.administer.user.application.port.service;

import com.arton.backend.administer.user.adapter.in.web.dto.UserDeleteByAdminDto;
import com.arton.backend.administer.user.adapter.in.web.dto.UserRegisterByAdminDto;
import com.arton.backend.administer.user.adapter.in.web.dto.UserUpdateByAdminDto;
import com.arton.backend.administer.user.application.port.in.ManageUserUseCase;
import com.arton.backend.administer.user.application.port.out.ManageUserPort;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManageUserService implements ManageUserUseCase {

    private final ManageUserPort manageUserPort;

    @Override
    public List<User> allUser() {
        return manageUserPort.allUser().orElseGet(ArrayList::new).stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User userDetail(Long id) {
        return UserMapper.toDomain(
                manageUserPort.userDetail(id)
                    .orElseThrow(() -> new CustomException("존재하지 않는 유저입니다.", ErrorCode.USER_NOT_FOUND))
        );
    }

    @Override
    public void registerUserByAdmin(UserRegisterByAdminDto userRegisterByAdminDto) {
        manageUserPort.saveUser(userRegisterByAdminDto.toEntity());
    }

    @Override
    public void updateUserByAdmin(UserUpdateByAdminDto userUpdateByAdminDto) {
        manageUserPort.updateUserByAdmin(userUpdateByAdminDto);
    }

    @Override
    public void deleteUserByAdmin(UserDeleteByAdminDto userDeleteByAdminDto) {
        UserEntity entity = userDeleteByAdminDto.toEntity();
        manageUserPort.deleteUserByAdmin(entity);
    }
}
