package com.arton.backend.user.application.port.in;

public interface MyPageUseCase {
    MyPageDto getMyPageInfo(long userId);
}
