package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.port.in.TermsShowDto;
import com.arton.backend.auth.application.port.in.TermsUseCase;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TermsService implements TermsUseCase {
    private final FileUploadUtils fileUploadUtils;

    @Override
    public List<TermsShowDto> getTerms() {
        List<TermsShowDto> response = new ArrayList<>();
        List<String> collect = fileUploadUtils.getFileNameInDirectory("arton/terms");
        for (String uri : collect) {
            String title = uri.substring(uri.lastIndexOf("/")+1, uri.indexOf(".html"));
            if (uri.contains("mandatory")) {
                response.add(TermsShowDto.builder()
                        .type("필수")
                        .title(title)
                        .uri(uri)
                        .build());
            } else {
                response.add(TermsShowDto.builder()
                        .type("선택")
                        .title(title)
                        .uri(uri)
                        .build());
            }
        }
        return response;
    }

}
