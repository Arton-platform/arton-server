package com.arton.backend.administer.banner.application.out;

import java.util.Optional;

import com.arton.backend.administer.banner.domain.BannerEntity;

public interface BannerSelectOntPort {

    Optional<BannerEntity> selectOneBanner(long id);
}
