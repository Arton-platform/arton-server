package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.data.OAuthSignupDto;
import com.arton.backend.auth.application.data.TokenDto;
import com.arton.backend.auth.application.port.in.KaKaoUseCase;
import com.arton.backend.auth.application.port.in.NaverUseCase;
import com.arton.backend.auth.application.port.in.OAuthUseCase;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuthUseCase {
    private final KaKaoUseCase kakaoService;
    private final NaverUseCase naverService;

    @Override
    public TokenDto signup(HttpServletRequest request, OAuthSignupDto signupDto) {
        if (signupDto.getLoginType().equals("0")) {
            return kakaoService.login(request, signupDto);
        } else if (signupDto.getLoginType().equals("1")) {
            return naverService.login(request, signupDto);
        } else if (signupDto.getLoginType().equals("2")) {

        }
        throw new CustomException(ErrorCode.PARAMETER_NOT_VALID.getMessage(), ErrorCode.PARAMETER_NOT_VALID);
    }
}
