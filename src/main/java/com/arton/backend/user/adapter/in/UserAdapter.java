package com.arton.backend.user.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.user.application.port.in.UserPasswordEditDto;
import com.arton.backend.user.application.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
