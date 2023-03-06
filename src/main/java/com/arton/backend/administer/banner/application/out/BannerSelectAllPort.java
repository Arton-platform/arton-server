package com.arton.backend.administer.banner.application.out;

import java.util.List;
import java.util.Optional;

import com.arton.backend.administer.banner.domain.BannerEntity;

public interface BannerSelectAllPort {

    Optional<List<BannerEntity>> selectAllBanner();

}
