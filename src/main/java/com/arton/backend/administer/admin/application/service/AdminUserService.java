package com.arton.backend.administer.admin.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.arton.backend.administer.admin.application.data.AdminSignupDTO;
import com.arton.backend.auth.application.data.LoginRequestDto;
import com.arton.backend.auth.application.data.TokenDto;
import com.arton.backend.image.application.port.out.UserImageSaveRepositoryPort;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.jwt.TokenProvider;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadUtils fileUploadUtils;
    private final UserImageSaveRepositoryPort userImageSaveRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    @Value("${refresh.token.prefix}")
    private String refreshTokenPrefix;
    private final RedisTemplate redisTemplate;

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
    public void registV2(AdminSignupDTO adminSignupDTO) {
        if (checkEmailDup(adminSignupDTO.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_IS_EXIST.getMessage(), ErrorCode.EMAIL_IS_EXIST);
        }
        if (!checkPassword(adminSignupDTO.getPassword(), adminSignupDTO.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH.getMessage(), ErrorCode.PASSWORD_NOT_MATCH);
        }

        User user = AdminSignupDTO.toUser(adminSignupDTO, passwordEncoder);
        User savedUser = userRepository.save(user);
        Long id = savedUser.getId();
        // 기본 이미지 지정후 image 저장
        UserImage userImage = UserImage.builder().imageUrl(fileUploadUtils.getDefaultImageUrl()).user(savedUser).build();
        userImage = userImageSaveRepository.save(userImage);

    }

    @Override
    public TokenDto login(LoginRequestDto loginRequestDto) {
        // 패스워드, 이메일 일치여부 확인
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        // password 불일치
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_INFO_NOT_MATCHED.getMessage(), ErrorCode.LOGIN_INFO_NOT_MATCHED);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(), loginRequestDto.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateForAdminToken(authenticate);
        redisTemplate.opsForValue().set(refreshTokenPrefix+authenticate.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
        return tokenDto;
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

    private boolean checkEmailDup(String email){
        return userRepository.checkEmailDup(email);
    }

    private boolean checkPassword(String password, String checkPassword){
        return password.equals(checkPassword);
    }

}
