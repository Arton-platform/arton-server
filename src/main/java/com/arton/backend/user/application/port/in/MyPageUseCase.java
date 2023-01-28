package com.arton.backend.user.application.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface MyPageUseCase {
    MyPageDto getMyPageInfo(long userId);
    void updateUserProfile(long userId, UserProfileEditDto userProfileEditDto, MultipartFile multipartFile);
}
