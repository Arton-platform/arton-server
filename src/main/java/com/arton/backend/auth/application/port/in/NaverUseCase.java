package com.arton.backend.auth.application.port.in;

import com.arton.backend.auth.application.data.TokenDto;

public interface NaverUseCase {
    TokenDto login(String code, String state);
}
