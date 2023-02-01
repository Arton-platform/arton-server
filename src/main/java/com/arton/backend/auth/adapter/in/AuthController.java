package com.arton.backend.auth.adapter.in;

import com.arton.backend.auth.application.port.in.*;
import com.arton.backend.infra.mail.EmailUseCase;
import com.arton.backend.infra.mail.MailDto;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "AUTH", description = "회원등록 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final KaKaoUseCase kaKaoUseCase;
    private final NaverUseCase naverUseCase;
    private final AuthUseCase authUseCase;
    private final EmailUseCase emailUseCase;

    /**
     * US-7
     * @param code
     * @return
     */
    @Hidden
    @GetMapping("/kakao")
    public ResponseEntity<CommonResponse> loginByKakao(@RequestParam String code){
        return ResponseEntity.ok(CommonResponse.builder().message(code).status(HttpStatus.OK.value()).build());
    }

    @Operation(summary = "카카오 간편 회원가입", description = "카카오 아이디로 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
            content = @Content(schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "500", description = "회원가입 처리 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/kakao/signup")
    public ResponseEntity<TokenDto> signupByKaKao(@RequestBody @Valid OAuthSignupDto signupDto) {
        TokenDto tokenDto = kaKaoUseCase.login(signupDto);
        return ResponseEntity.ok(tokenDto);
    }

    /**
     * US-7
     * @param code
     * @param state
     * @return
     */
    @Hidden
    @GetMapping("/naver")
    public ResponseEntity<CommonResponse> loginByNaver(@RequestParam String code, @RequestParam String state){
        return ResponseEntity.ok(CommonResponse.builder().message(code+" "+state).status(HttpStatus.OK.value()).build());
    }

    @Operation(summary = "카카오 간편 회원가입", description = "카카오 아이디로 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "500", description = "회원가입 처리 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/naver/signup")
    public ResponseEntity<TokenDto> loginByNaver(@RequestBody @Valid OAuthSignupDto signupDto){
        TokenDto tokenDto = naverUseCase.login(signupDto);
        return ResponseEntity.ok(tokenDto);
    }

    /**
     * US-8, 14
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

    /**
     * 프론트에서 다음 버튼을 누를때 패스워드 이메일 유효성을 검증하는 기능
     * @param signupValidationDto
     * @return
     */
    @PostMapping(value = "/check/signup")
    public ResponseEntity<CommonResponse> checkPasswordAndEmail(@RequestBody @Valid SignupValidationDto signupValidationDto) {
        authUseCase.validateSignupRequest(signupValidationDto);
        CommonResponse commonResponse = CommonResponse.builder().message("올바른 입력값입니다.").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * US-7
     * 로그인
     * @param loginRequestDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authUseCase.login(loginRequestDto));
    }

    /**
     * US-15 패스워드 찾기
     * @param passwordResetDto
     * @return
     */
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

    /**
     * US-22 로그아웃
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(HttpServletRequest request) {
        authUseCase.logout(request);
        CommonResponse response = CommonResponse.builder().message("로그아웃 하였습니다.").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    /**
     * 리프레쉬 토큰과, 액세스 토큰을 이용하여 사용자의 로그인 상태를 체크합니다.
     * @param tokenReissueDto
     * @return
     */
    @PostMapping("/check/login")
    public ResponseEntity<TokenDto> checkStatus(@RequestBody TokenReissueDto tokenReissueDto) {
        return ResponseEntity.ok(authUseCase.reissue(tokenReissueDto));
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<CommonResponse> withdraw(HttpServletRequest request, @RequestBody @Valid WithdrawalRequestDto withdrawalRequestDto) {
        authUseCase.withdraw(request, withdrawalRequestDto);
        return ResponseEntity.ok(CommonResponse.builder().message("성공적으로 회원 탈퇴를 하였습니다.").status(HttpStatus.NO_CONTENT.value()).build());
    }
}
