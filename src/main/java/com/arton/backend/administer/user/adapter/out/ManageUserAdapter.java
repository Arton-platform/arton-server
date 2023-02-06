package com.arton.backend.administer.user.adapter.out;

import com.arton.backend.administer.user.adapter.in.web.dto.UserUpdateByAdminDto;
import com.arton.backend.administer.user.application.port.out.ManageUserPort;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ManageUserAdapter implements ManageUserPort {

    private final UserRepository userRepository;


    @Override
    public Optional<List<UserEntity>> allUser() {
        return Optional.of(userRepository.findAll());
    }

    @Override
    public Optional<UserEntity> userDetail(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveUser(UserEntity entity) {
        userRepository.save(entity);
    }

    @Override
    public void updateUserByAdmin(UserUpdateByAdminDto userUpdateByAdminDto) {
        UserEntity originalEntity = userRepository.findById(userUpdateByAdminDto.getId())
                .orElseThrow(()->new CustomException("존재하지 않는 유저입니다.", ErrorCode.USER_NOT_FOUND));
        UserEntity updateEntity = userUpdateByAdminDto.toEntity();

        originalEntity
                .changeEmail(updateEntity.getEmail())
                .setPassword(updateEntity.getPassword())
                .changeNickName(updateEntity.getNickname())
                .changeGender(updateEntity.getGender())
                .changeAgeRange(updateEntity.getAgeRange())
                .changeAuth(updateEntity.getAuth())
        ;
    }

    @Override
    public void deleteUserByAdmin(UserEntity entity) {
        userRepository.delete(entity);
    }
}
