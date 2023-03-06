package com.arton.backend.user.application.port.in;

import com.arton.backend.performance.applicaiton.data.PerformanceDetailDtoV2;
import com.arton.backend.user.application.data.MainPageDto;

import java.util.List;

public interface MainPageUseCase {
    MainPageDto getMainPage(Long userId);
}
