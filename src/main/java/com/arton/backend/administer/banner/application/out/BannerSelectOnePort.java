package com.arton.backend.administer.banner.application.out;


import com.arton.backend.administer.banner.domain.BannerEntity;

public interface BannerSelectOnePort {

    BannerEntity selectOneBanner(long id);
}
