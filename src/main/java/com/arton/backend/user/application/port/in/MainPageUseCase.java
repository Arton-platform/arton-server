package com.arton.backend.user.application.port.in;

import com.arton.backend.user.application.data.MainPageDto;

public interface MainPageUseCase {
    MainPageDto getMainPage(Long userId, int offset, int limit);
}
