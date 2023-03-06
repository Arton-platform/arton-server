package com.arton.backend.administer.banner.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arton.backend.administer.banner.application.in.BannerDeleteUseCase;
import com.arton.backend.administer.banner.application.in.BannerRegistUseCase;
import com.arton.backend.administer.banner.application.in.BannerSelectAllUseCase;
import com.arton.backend.administer.banner.application.in.BannerSelectOneUseCase;
import com.arton.backend.administer.banner.application.in.BannerUpdateUseCase;
import com.arton.backend.administer.banner.domain.Banner;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerRegistUseCase registUseCase;
    private final BannerSelectAllUseCase bannerSelectAllUseCase;
    private final BannerSelectOneUseCase bannerSelectOneUseCase;
    private final BannerUpdateUseCase bannerUpdateUseCase;
    private final BannerDeleteUseCase bannerDeleteUseCase;

    @PostMapping("/regist")
    public CommonResponse regist(Banner banner){

        registUseCase.registBanner(banner);

        return CommonResponse.builder()
            .message("SUCCESS")
            .status(HttpStatus.OK.value())
            .build();
    }

    @GetMapping("/banners")
    public ResponseData<List<Banner>> banners () {
        return new ResponseData<>(
            "SUCCESS",
            HttpStatus.OK.value(),
            bannerSelectAllUseCase.selectAllBanner()
        );
    }

    @GetMapping("/banners/{id}")
    public ResponseData<Banner> banner(@PathVariable("id") Long id){
        return new ResponseData<>(
            "SUCCESS",
            HttpStatus.OK.value(),
            bannerSelectOneUseCase.selectOneBanner(id)
        );
    }
    
    @PutMapping("/banner-update")
    public CommonResponse updateBanner(Banner banner){

        bannerUpdateUseCase.updateBanner(banner);

        return CommonResponse.builder()
            .message("SUCCESS")
            .status(HttpStatus.OK.value())
            .build();
    }

    @DeleteMapping("/banners/{id}")
    public CommonResponse deleteBanner(@PathVariable("id") Long id){

        bannerDeleteUseCase.deleteBanner(id);

        return CommonResponse.builder()
            .message("SUCCESS")
            .status(HttpStatus.OK.value())
            .build();
    }

}
