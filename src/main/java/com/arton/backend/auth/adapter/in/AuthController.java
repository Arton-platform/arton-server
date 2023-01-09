package com.arton.backend.auth.adapter.in;

import com.arton.backend.auth.application.port.in.*;
import com.arton.backend.infra.mail.EmailUseCase;
import com.arton.backend.infra.mail.MailDto;
import com.arton.backend.infra.shared.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final KaKaoUseCase kaKaoUseCase;
    private final NaverUseCase naverUseCase;
    private final AuthUseCase authUseCase;
    private final EmailUseCase emailUseCase;

    @GetMapping("/kakao")
    public ResponseEntity<TokenDto> loginByKakao(@RequestParam String code){
        log.info("code {}", code);
        TokenDto tokenDto = kaKaoUseCase.login(code);
        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/naver")
    public ResponseEntity<TokenDto> loginByNaver(@RequestParam String code, @RequestParam String state){
        log.info("code {}", code);
        log.info("state {}", state);
        TokenDto login = naverUseCase.login(code, state);
        return ResponseEntity.ok(login);
    }

    /**
     * 회원가입
     * 200 성공
     * 400 패스워드 불일치
     * 409 이메일 중복
     * @return
     */
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> signUp(@RequestPart(required = true, name = "signupRequestDto") @Valid SignupRequestDto signupRequestDto, @RequestPart(required = false, name = "image") MultipartFile multipartFile) {
        authUseCase.signup(signupRequestDto, multipartFile);
        CommonResponse commonResponse = CommonResponse.builder().message("회원가입에 성공하였습니다").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authUseCase.login(loginRequestDto));
    }

    @PutMapping("/reset/password")
    public ResponseEntity<CommonResponse> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        MailDto mailDto = authUseCase.resetPassword(passwordResetDto);
        emailUseCase.sendMailByHTML(mailDto);
        CommonResponse commonResponse = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공적으로 메일을 보냈습니다.")
                .build();
        return ResponseEntity.ok(commonResponse);
    }
}
