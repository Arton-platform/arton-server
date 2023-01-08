package com.arton.backend.infra.mail;

import com.arton.backend.infra.shared.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class EmailController {
    private final EmailUseCase emailUseCase;

    @PostMapping("/reset/password")
    public ResponseEntity<CommonResponse> sendPassword(@RequestBody MailDto mailDto) {
        emailUseCase.sendMailOnlyText(mailDto);
        CommonResponse build = CommonResponse.builder()
                .message("메일을 성공적으로 보냈습니다.")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(build);
    }
}