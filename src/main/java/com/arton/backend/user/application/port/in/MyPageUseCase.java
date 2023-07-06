package com.arton.backend.user.application.port.in;

import com.arton.backend.user.application.data.MyPageDto;
import com.arton.backend.user.application.data.UserProfileEditDto;
import org.springframework.web.multipart.MultipartFile;

public interface MyPageUseCase {
    MyPageDto getMyPageInfo(long userId);
    MyPageDto getOtherUserInfo(long userId, long toId);
    void updateUserProfile(long userId, UserProfileEditDto userProfileEditDto, MultipartFile multipartFile);
}
