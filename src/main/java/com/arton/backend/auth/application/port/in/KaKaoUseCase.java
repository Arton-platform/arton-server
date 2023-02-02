package com.arton.backend.auth.application.port.in;

import com.arton.backend.auth.application.data.OAuthSignupDto;
import com.arton.backend.auth.application.data.TokenDto;

public interface KaKaoUseCase {
    TokenDto login(OAuthSignupDto signupDto);
}
