package com.arton.backend.auth.application.port.in;

public interface KaKaoUseCase {
    TokenDto login(String accessToken);
    TokenDto loginDupTest(String code);
}
