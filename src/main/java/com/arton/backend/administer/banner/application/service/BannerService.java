package com.arton.backend.administer.banner.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arton.backend.administer.banner.application.in.BannerDeleteUseCase;
import com.arton.backend.administer.banner.application.in.BannerRegistUseCase;
import com.arton.backend.administer.banner.application.in.BannerSelectAllUseCase;
import com.arton.backend.administer.banner.application.in.BannerSelectOneUseCase;
import com.arton.backend.administer.banner.application.in.BannerUpdateUseCase;
import com.arton.backend.administer.banner.application.out.BannerDeletePort;
import com.arton.backend.administer.banner.application.out.BannerRegistPort;
import com.arton.backend.administer.banner.application.out.BannerSelectAllPort;
import com.arton.backend.administer.banner.application.out.BannerSelectOntPort;
import com.arton.backend.administer.banner.application.out.BannerUpdatePort;
import com.arton.backend.administer.banner.domain.Banner;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannerService implements BannerRegistUseCase, BannerSelectAllUseCase, BannerSelectOneUseCase, BannerUpdateUseCase, BannerDeleteUseCase{
    
    private final BannerRegistPort registPort;
    private final BannerSelectAllPort selectAllPort;
    private final BannerSelectOntPort selectOnePort;
    private final BannerUpdatePort updatePort;
    private final BannerDeletePort deletePort;

    @Override
    public void registBanner(Banner banner) {
        // Mapper
        registPort.registBanner(banner);
    }

    @Override
    public Banner selectOneBanner(long id) {
        selectOnePort.selectOneBanner(id);
        // Mapper
        return null;
    }

    @Override
    public List<Banner> selectAllBanner() {
        selectAllPort.selectAllBanner();
        // Mapper
        return null;
    }

    @Override
    public void updateBanner(Banner banner) {
        // Mapper
        updatePort.updateBanner(banner);
    }

    @Override
    public void deleteBanner(Long id) {
        deletePort.deleteBanner(id);
    }
}
