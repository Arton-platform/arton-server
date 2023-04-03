package com.arton.backend.administer.banner.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arton.backend.administer.banner.application.in.BannerDeleteUseCase;
import com.arton.backend.administer.banner.application.in.BannerRegistUseCase;
import com.arton.backend.administer.banner.application.in.BannerSelectAllUseCase;
import com.arton.backend.administer.banner.application.in.BannerSelectOneUseCase;
import com.arton.backend.administer.banner.application.in.BannerUpdateUseCase;
import com.arton.backend.administer.banner.application.out.BannerDeletePort;
import com.arton.backend.administer.banner.application.out.BannerRegistPort;
import com.arton.backend.administer.banner.application.out.BannerSelectAllPort;
import com.arton.backend.administer.banner.application.out.BannerSelectOnePort;
import com.arton.backend.administer.banner.domain.Banner;
import com.arton.backend.administer.banner.domain.BannerEntity;
import com.arton.backend.administer.banner.domain.BannerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannerService implements BannerRegistUseCase, BannerSelectAllUseCase, BannerSelectOneUseCase, BannerUpdateUseCase, BannerDeleteUseCase{
    
    private final BannerRegistPort registPort;
    private final BannerSelectAllPort selectAllPort;
    private final BannerSelectOnePort selectOnePort;
    private final BannerDeletePort deletePort;

    private final BannerMapper mapper;

    @Override
    public void registBanner(Banner banner) {
        registPort.registBanner(mapper.toEntity(banner));
    }

    @Override
    public Banner selectOneBanner(long id) {
        return mapper.toDomain(
            selectOnePort.selectOneBanner(id)
        );
    }

    @Override
    public List<Banner> selectAllBanner() {
        return selectAllPort.selectAllBanner()
            .stream()
            .map(entity -> mapper.toDomain(entity))
            .collect(Collectors.toList());
    }

    @Override
    public void updateBanner(Banner banner) {
        BannerEntity original = selectOnePort.selectOneBanner(banner.getId());
        original.update(
            banner.getSeq(),
            banner.getTitle(),
            banner.getIsActive()
        );
    }

    @Override
    public void deleteBanner(Long id) {
        deletePort.deleteBanner(id);
    }
}
