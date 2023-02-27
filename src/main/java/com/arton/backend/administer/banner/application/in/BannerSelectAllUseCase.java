package com.arton.backend.administer.banner.application.in;

import java.util.List;

import com.arton.backend.administer.banner.domain.Banner;

public interface BannerSelectAllUseCase {
    public List<Banner> selectAllBanner();
} 
