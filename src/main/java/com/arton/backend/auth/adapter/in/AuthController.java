package com.arton.backend.auth.adapter.in;

import com.arton.backend.auth.application.data.*;
import com.arton.backend.auth.application.port.in.*;
import com.arton.backend.infra.mail.EmailUseCase;
import com.arton.backend.infra.mail.MailDto;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
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
    private final OAuthUseCase oAuthUseCase;
    private final AuthUseCase authUseCase;
    private final EmailUseCase emailUseCase;

    /**
     * header에서 token 분리후 간편가입 진행
     * @param request
     * @param signupDto
     * @return
     */
    @Hidden
    @GetMapping("/singup/oauth")
    public ResponseEntity<TokenDto> loginByKakao(HttpServletRequest request, @RequestBody @Valid OAuthSignupDto signupDto){
        TokenDto tokenDto = oAuthUseCase.signup(request, signupDto);
        return ResponseEntity.ok(tokenDto);
    }

    /**
     * US-8, 14
     * 회원가입
     * 200 성공
     * 401 패스워드 불일치
     * 409 이메일 중복
     * @return
     */
    @Operation(summary = "회원가입", description = "앱 자체 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "빈파일 업로드",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "401", description = "패스워드 불일치",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "이메일 중복",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "이미지 업로드 실패",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
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
    @Operation(summary = "이메일/패스워드 검증", description = "패스워드/이메일 유효성을 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검증 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "401", description = "패스워드 불일치",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "이메일 중복",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(value = "/check/signup")
    public ResponseEntity<CommonResponse> checkPasswordAndEmail(@RequestBody @Valid SignupValidationDto signupValidationDto) {
        authUseCase.validateSignupRequest(signupValidationDto);
        CommonResponse commonResponse = CommonResponse.builder().message("올바른 입력값입니다.").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * US-7
     * 로그인 404 401
     * @param loginRequestDto
     * @return
     */
    @Operation(summary = "로그인", description = "로그인을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "401", description = "아이디 or 패스워드 불일치",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authUseCase.login(loginRequestDto));
    }

    /**
     * US-15 패스워드 찾기
     * @param passwordResetDto
     * @return
     */
    @Operation(summary = "패스워드 초기화", description = "패스워드 재발급을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "패스워드 초기화 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/reset/password")
    public ResponseEntity<CommonResponse> resetPassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
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
    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
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
    @Operation(summary = "로그인 상태 확인", description = "리프레쉬 토큰을 활용해 사용자의 로그인 상태를 체크합니다.(토큰 기간 만료시 재발급을 진행하여 로그인 상태 유지.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 상태 체크 완료",
                    content = @Content(schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/check/login")
    public ResponseEntity<TokenDto> checkStatus(@RequestBody TokenReissueDto tokenReissueDto) {
        return ResponseEntity.ok(authUseCase.reissue(tokenReissueDto));
    }


    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "금지된 요청(다른 유저 탈퇴 요청등..)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "이미지 로드 에러",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class)))})
    @PostMapping("/withdrawal")
    public ResponseEntity<CommonResponse> withdraw(HttpServletRequest request, @RequestBody @Valid WithdrawalRequestDto withdrawalRequestDto) {
        authUseCase.withdraw(request, withdrawalRequestDto);
        return ResponseEntity.ok(CommonResponse.builder().message("성공적으로 회원 탈퇴를 하였습니다.").status(HttpStatus.NO_CONTENT.value()).build());
    }
}
