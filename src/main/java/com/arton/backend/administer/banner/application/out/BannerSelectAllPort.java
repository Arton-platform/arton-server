package com.arton.backend.administer.banner.application.out;

import java.util.List;

import com.arton.backend.administer.banner.domain.BannerEntity;

public interface BannerSelectAllPort {

    List<BannerEntity> selectAllBanner();

}
