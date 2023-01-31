package com.arton.backend.auth.application.port.in;

public interface KaKaoUseCase {
    TokenDto login(String code);
    TokenDto loginDupTest(String code);
}
