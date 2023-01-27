package com.arton.backend.user.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.user.application.port.in.UserPasswordEditDto;
import com.arton.backend.user.application.port.in.UserProfileEditDto;
import com.arton.backend.user.application.port.in.UserUseCase;
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
     * UserProfileEditDto 수정된 값이 없더라도 GET 한 값을 그대로 보내주세요.
     * 더 좋은 방안이 있다면 수정 필요.
     * @param userDetails
     * @param userProfileEditDto
     * @param multipartFile
     * @return
     */
    @PutMapping(value = "/my/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> updateUserProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestPart(required = false, name = "data") @Valid UserProfileEditDto userProfileEditDto,
                                                            @RequestPart(required = false, name = "image")MultipartFile multipartFile){
        long userId = Long.parseLong(userDetails.getUsername());
        userService.updateUserProfile(userId, userProfileEditDto, multipartFile);
        CommonResponse response = CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

}
