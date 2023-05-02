package com.arton.backend.administer.user.application.port.service;

import com.arton.backend.administer.user.adapter.in.web.dto.UserUpdateByAdminDto;
import com.arton.backend.administer.user.application.port.in.ManageUserUseCase;
import com.arton.backend.administer.user.application.port.out.ManageUserPort;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import com.arton.backend.user.adapter.out.persistence.repository.UserRepository;
import com.arton.backend.user.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ManageUserServiceTest {

    @Autowired
    ManageUserPort manageUserPort;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void createUser(){
        userRepository.save(
                UserEntity.builder()
                        .auth(UserRole.ROLE_NORMAL)
                        .nickname("thdrmsqhd")
                        .email("thdrmsqhd@gmail.com")
                        .gender(Gender.MALE)
                        .ageRange(AgeRange.Age30_39)
                        .password("asdf")
                        .signupType(SignupType.ARTON)
                        .build()
        );
    }

    @Test
    void allUser() {
        List<User> userList = manageUserPort.allUser().orElseGet(ArrayList::new)
                        .stream()
                        .map(UserMapper::toDomain)
                        .collect(Collectors.toList());

        userList.forEach(System.out::println);

        assertNotNull(userList);
    }

    @Test
    void registerUserByAdmin() {

        UserEntity entity = UserEntity.builder()
                .email("thdrmsqhd2@gmail.com")
                .gender(Gender.MALE)
                .auth(UserRole.ROLE_NORMAL)
                .nickname("thdrmsqhd2")
                .ageRange(AgeRange.Age30_39)
                .password("asdf")
                .userStatus(true)
                .build();

        manageUserPort.saveUser(entity);
        UserEntity user2 = userRepository.findByEmail("thdrmsqhd2@gmail.com").get(0);

        assertEquals(user2.getEmail(), entity.getEmail());
    }

    @Test
    void updateUserByAdmin() {

        UserEntity entity = UserEntity.builder()
                .email("thdrmsqhd2@gmail.com")
                .gender(Gender.MALE)
                .auth(UserRole.ROLE_NORMAL)
                .nickname("thdrmsqhd2")
                .ageRange(AgeRange.Age30_39)
                .password("asdf")
                .userStatus(true)
                .build();

        manageUserPort.saveUser(entity);

        UserEntity user2 = userRepository.findByEmail("thdrmsqhd2@gmail.com").get(0);

        UserUpdateByAdminDto dto = UserUpdateByAdminDto.builder()
                .id(user2.getId())
                .gender(user2.getGender())
                .email("thdrmsqhd3@gmail.com")
                .ageRange(user2.getAgeRange())
                .auth(user2.getAuth())
                .nickname(user2.getNickname())
                .password(user2.getPassword())
                .build();
        manageUserPort.updateUserByAdmin(dto);

        UserEntity user3 = userRepository.findByEmail("thdrmsqhd3@gmail.com").get(0);

        assertEquals(dto.getEmail(), user3.getEmail());
    }

    @Test
    void deleteUserByAdmin() {
        UserEntity entity = UserEntity.builder()
                .email("thdrmsqhd2@gmail.com")
                .gender(Gender.MALE)
                .auth(UserRole.ROLE_NORMAL)
                .nickname("thdrmsqhd2")
                .ageRange(AgeRange.Age30_39)
                .password("asdf")
                .userStatus(true)
                .build();

        manageUserPort.saveUser(entity);

        UserEntity user2 = userRepository.findByEmail("thdrmsqhd2@gmail.com").get(0);

        manageUserPort.deleteUserByAdmin(user2);

    }
}