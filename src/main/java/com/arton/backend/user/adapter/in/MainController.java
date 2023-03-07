package com.arton.backend.user.adapter.in;

import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.user.application.data.MainPageDto;
import com.arton.backend.user.application.port.in.MainPageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainPageUseCase mainPageUseCase;

    @GetMapping("/home")
    public ResponseEntity<ResponseData<MainPageDto>> goHome(@AuthenticationPrincipal UserDetails userDetails) {
        long id = Long.parseLong(userDetails.getUsername());
        MainPageDto mainPage = mainPageUseCase.getMainPage(id);
        ResponseData responseData = new ResponseData("SUCCESS", HttpStatus.OK.value(), mainPage);
        return ResponseEntity.ok(responseData);
    }
}
