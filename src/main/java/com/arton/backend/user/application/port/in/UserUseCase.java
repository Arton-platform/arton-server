package com.arton.backend.user.application.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface UserUseCase {
    void changePassword(Long userID, UserPasswordEditDto editDto);
    Boolean alertState(long userId);
    void updateUserProfile(long userId, UserProfileEditDto userProfileEditDto, MultipartFile multipartFile);
    void updateAlertState(long userId, Boolean state);
    MyPageDto getMyPageInfo(long userId);
}
