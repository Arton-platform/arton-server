package com.arton.backend.user.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.user.application.port.in.UserPasswordEditDto;
import com.arton.backend.user.application.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAdapter {
    private final UserUseCase userService;

    @PatchMapping("/my/password")
    public ResponseEntity<CommonResponse> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserPasswordEditDto userPasswordEditDto) {
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
}
