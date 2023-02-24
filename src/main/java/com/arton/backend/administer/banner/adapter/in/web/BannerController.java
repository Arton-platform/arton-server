package com.arton.backend.administer.banner.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arton.backend.administer.banner.application.in.BannerRegistUseCase;
import com.arton.backend.administer.banner.domain.Banner;
import com.arton.backend.infra.shared.common.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerRegistUseCase registUseCase;

    @PostMapping("/regist")
    public CommonResponse regist(Banner banner){
        return CommonResponse.builder()
        .message(null)
        .status(null)
        .build();
    }
    
}
