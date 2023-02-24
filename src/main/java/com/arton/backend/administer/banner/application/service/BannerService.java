package com.arton.backend.administer.banner.application.service;

import org.springframework.stereotype.Service;

import com.arton.backend.administer.banner.application.in.BannerRegistUseCase;
import com.arton.backend.administer.banner.domain.Banner;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannerService implements BannerRegistUseCase{@Override
    public void registBanner(Banner banner) {

    }
}
