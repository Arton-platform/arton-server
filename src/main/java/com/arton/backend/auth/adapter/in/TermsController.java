package com.arton.backend.auth.adapter.in;

import com.arton.backend.auth.application.port.in.TermsShowDto;
import com.arton.backend.auth.application.port.in.TermsUseCase;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/terms")
@RequiredArgsConstructor
public class TermsController {
    private final TermsUseCase termsService;
    /**
     * 약관 리스트 보여주기
     * 선택은 url webview로 넘겨주자.
     * @return
     */
    @GetMapping
    @ResponseBody
    public ResponseData<List<TermsShowDto>> showTermList() {
        List<TermsShowDto> response = termsService.getTerms();
        return new ResponseData<>("SUCCESS", HttpStatus.OK.value(), response);
    }
}