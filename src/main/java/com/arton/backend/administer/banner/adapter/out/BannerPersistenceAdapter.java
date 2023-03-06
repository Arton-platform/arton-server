package com.arton.backend.administer.banner.adapter.out;

import java.util.List;
import java.util.Optional;

import com.arton.backend.administer.banner.application.out.*;
import org.springframework.stereotype.Repository;

import com.arton.backend.administer.banner.domain.BannerEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BannerPersistenceAdapter implements BannerRegistPort, BannerSelectOnePort, BannerSelectAllPort, BannerDeletePort, BannerUpdatePort {

    private final BannerRepository repository;

    @Override
    public void deleteBanner(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<List<BannerEntity>> selectAllBanner() {
        return Optional.ofNullable(repository.findAll());
    }

    @Override
    public Optional<BannerEntity> selectOneBanner(long id) {
        return repository.findById(id);
    }

    @Override
    public void registBanner(BannerEntity bannerEntity) {
        repository.save(bannerEntity);
    }

    @Override
    public void updateBanner(BannerEntity banner) {
        repository.save(banner);
    }
}
