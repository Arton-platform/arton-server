package com.arton.backend.auth.adapter.in;

import com.arton.backend.auth.application.port.in.TermsShowDto;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/terms")
@RequiredArgsConstructor
public class TermsController {

    private final ResourceLoader resourceLoader;

    /**
     * 약관 리스트 보여주기
     * @return
     */
    @GetMapping
    @ResponseBody
    public ResponseData<List<TermsShowDto>> showTermList() {
        List<String> collect = new ArrayList<>();
        List<TermsShowDto> response = new ArrayList<>();
        try {
            collect = Arrays.stream(resourceLoader.getResource("classpath:templates/terms").getFile().listFiles()).filter(file -> !file.isDirectory()).map(File::getName).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : collect) {
            String uri = s.substring(0, s.lastIndexOf("."));
            if (s.contains("mandatory")) {
                response.add(TermsShowDto.builder()
                        .mandatory("필수")
                        .title(s)
                        .uri("/terms/" + uri)
                        .build());
            } else {
                response.add(TermsShowDto.builder()
                        .mandatory("선택")
                        .title(s)
                        .uri("/terms/" + uri)
                        .build());
            }
        }
        return new ResponseData<>("SUCCESS", HttpStatus.OK.value(), response);
    }

    /**
     * 약관 리스트 web view로 넘기기
     * @param termsName
     * @return
     */
    @GetMapping("/{termsName}")
    public String getTerms(@PathVariable(name = "termsName") String termsName) {
        List<String> collect = new ArrayList<>();
        try {
            collect = Arrays.stream(resourceLoader.getResource("classpath:templates/terms").getFile().listFiles()).filter(file -> !file.isDirectory()).map(File::getName).collect(Collectors.toList());
            if (collect.stream().anyMatch(name -> name.equals(termsName))) {
                return "/terms/" + termsName;
            }
        } catch (IOException e) {
            log.error("GET RESOURCE ERROR {}", e);
        }
        throw new CustomException(ErrorCode.INVALID_URI_REQUEST.getMessage(), ErrorCode.INVALID_URI_REQUEST);
    }

}