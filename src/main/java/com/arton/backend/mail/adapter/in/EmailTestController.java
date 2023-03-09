package com.arton.backend.mail.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.mail.application.port.in.EmailUseCase;
import com.arton.backend.mail.application.data.MailDto;
import com.arton.backend.mail.application.data.MailMultiReceiversDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 메일 기능을 테스트하는 controller
 */
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class EmailTestController {
    private final EmailUseCase emailUseCase;

    @PostMapping("/send")
    public ResponseEntity<CommonResponse> sendPassword(@RequestBody MailDto mailDto) {
        emailUseCase.sendMailOnlyText(mailDto);
        CommonResponse build = CommonResponse.builder()
                .message("메일을 성공적으로 보냈습니다.")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(build);
    }

    @PostMapping("/send/multi")
    public ResponseEntity<CommonResponse> multiSend(@RequestBody MailMultiReceiversDto mailDto) {
        emailUseCase.sendMailWithMultipleReceivers(mailDto);
        CommonResponse build = CommonResponse.builder()
                .message("메일을 성공적으로 보냈습니다.")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(build);
    }

    @PostMapping("/send/html")
    public ResponseEntity<CommonResponse> sendPasswordHtmlForm(@RequestBody MailDto mailDto) {
        emailUseCase.sendPasswordMailByHTML(mailDto);
        CommonResponse build = CommonResponse.builder()
                .message("메일을 성공적으로 보냈습니다.")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(build);
    }
}
