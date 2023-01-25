package com.arton.backend.user.application.service;

import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.application.port.in.UserPasswordEditDto;
import com.arton.backend.user.application.port.in.UserProfileEditDto;
import com.arton.backend.user.application.port.in.UserUseCase;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadUtils fileUploadUtils;

    @Override
    public void changePassword(Long userId, UserPasswordEditDto editDto) {
        User user = findUser(userId);
        if (!checkPassword(editDto.getPassword(), editDto.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH.getMessage(), ErrorCode.PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(editDto.getPassword()));
        userRepository.save(user);
    }

    public boolean checkPassword(String password, String checkPassword){
        return password.equals(checkPassword);
    }

    private User findUser(Long userId) {
       return userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Boolean alertState(long userId) {
        return findUser(userId).getAlertState();
    }

    /**
     * ART-21
     * 닉네임, 한줄소개, 이미지를 업데이트
     * @param userId
     * @param userProfileEditDto
     * @param multipartFile
     */
    @Override
    public void updateUserProfile(long userId, UserProfileEditDto userProfileEditDto, MultipartFile multipartFile) {
        User user = findUser(userId);
        // 이미지 업데이트
        if (multipartFile != null) {
            fileUploadUtils.delete(user.getId(), user.getProfileImageUrl());
            String upload = fileUploadUtils.upload(multipartFile, "arton/image/profiles/" + user.getId());
            user.setProfileImageUrl(upload);
        }
        user.updateProfile(userProfileEditDto);
        userRepository.save(user);
    }

    @Override
    public void updateAlertState(long userId, Boolean state) {
        User user = findUser(userId);
        user.changeAlertState(state);
    }
}
