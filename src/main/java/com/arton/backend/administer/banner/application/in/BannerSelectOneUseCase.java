package com.arton.backend.administer.banner.application.in;

import com.arton.backend.administer.banner.domain.Banner;

public interface BannerSelectOneUseCase {
    public Banner selectOneBanner(long id);
}
