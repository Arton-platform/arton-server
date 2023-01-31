package com.arton.backend.auth.application.port.in;

public interface NaverUseCase {
    TokenDto login(String code, String status);
}
