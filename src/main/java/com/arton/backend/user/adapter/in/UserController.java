package com.arton.backend.user.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import com.arton.backend.user.application.data.MyPageDto;
import com.arton.backend.user.application.data.UserPasswordEditDto;
import com.arton.backend.user.application.data.UserProfileEditDto;
import com.arton.backend.user.application.port.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserUseCase userService;
    private final MyPageUseCase myPageService;


    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "패스워드 변경", description = "패스워드를 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "패스워드 변경 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "401", description = "정보 불일치",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PatchMapping("/my/password")
    public ResponseEntity<CommonResponse> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UserPasswordEditDto userPasswordEditDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        userService.changePassword(userId, userPasswordEditDto);
        CommonResponse response = CommonResponse.builder()
                .message("성공적으로 비밀번호를 변경하였습니다.")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @Parameter(name = "userDetails", hidden = true)
    @GetMapping("/my/alert-state")
    public ResponseEntity<ResponseData<Boolean>> alertState(@AuthenticationPrincipal UserDetails userDetails){
        long userId = Long.parseLong(userDetails.getUsername());
        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                userService.alertState(userId)
        );
        return ResponseEntity.ok(response);
    }

    @Parameter(name = "userDetails", hidden = true)
    @PutMapping("/my/update-alert-state")
    public ResponseEntity<CommonResponse> updateAlertState(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Boolean state){
        long userId = Long.parseLong(userDetails.getUsername());
        userService.updateAlertState(userId, state);
        CommonResponse response = CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * 마이페이지
     * @param userDetails
     * @return
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "마이페이지", description = "마이페이지 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 정보 반환 성공",
                    content = @Content(schema = @Schema(implementation = MyPageDto.class))),
            @ApiResponse(responseCode = "404", description = "유저 정보 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 처리 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/my/profile")
    public ResponseEntity<ResponseData<MyPageDto>> getMyPage(@AuthenticationPrincipal UserDetails userDetails) {
        long userId = Long.parseLong(userDetails.getUsername());
        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                myPageService.getMyPageInfo(userId)
        );
        return ResponseEntity.ok(response);
    }

    /**
     * 유저 누르면 해당 유저의 마이페이지로
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "타유저의 페이지", description = "타유저의 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "타유저 정보 반환 성공",
                    content = @Content(schema = @Schema(implementation = MyPageDto.class))),
            @ApiResponse(responseCode = "404", description = "유저 정보 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 처리 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/profile/{id}")
    public ResponseEntity<ResponseData<MyPageDto>> getSpecificUserPage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "id", required = true) Long id) {
        long userId = Long.parseLong(userDetails.getUsername());
        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                myPageService.getOtherUserInfo(userId, id)
        );
        return ResponseEntity.ok(response);
    }

    /**
     * UserProfileEditDto 수정된 값이 없더라도 GET 한 값을 그대로 보내주세요.
     * 더 좋은 방안이 있다면 수정 필요.
     * @param userDetails
     * @param userProfileEditDto
     * @param multipartFile
     * @return
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "프로필 정보 변경", description = "유저의 프로필 정보를 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저 정보 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 처리 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping(value = "/my/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> updateUserProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestPart(required = false, name = "data") @Valid UserProfileEditDto userProfileEditDto,
                                                            @RequestPart(required = false, name = "image")MultipartFile multipartFile){
        long userId = Long.parseLong(userDetails.getUsername());
        myPageService.updateUserProfile(userId, userProfileEditDto, multipartFile);
        CommonResponse response = CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

}
