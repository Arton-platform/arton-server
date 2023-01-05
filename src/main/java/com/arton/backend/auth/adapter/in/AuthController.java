package com.arton.backend.auth.adapter.in;

import com.arton.backend.auth.application.port.in.KaKaoUseCase;
import com.arton.backend.auth.application.port.in.NaverUseCase;
import com.arton.backend.auth.application.port.in.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final KaKaoUseCase kaKaoUseCase;
    private final NaverUseCase naverUseCase;

    @GetMapping("/kakao")
    public ResponseEntity<TokenDto> getCode(@RequestParam String code){
        log.info("code {}", code);
        TokenDto tokenDto = kaKaoUseCase.login(code);
        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/naver")
    public ResponseEntity<TokenDto> loginWithNaver(@RequestParam String code, @RequestParam String state){
        log.info("code {}", code);
        log.info("state {}", state);
        TokenDto login = naverUseCase.login(code, state);
        return ResponseEntity.ok(login);
    }
}