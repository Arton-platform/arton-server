package com.arton.backend.administer.banner.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.arton.backend.administer.banner.application.out.BannerDeletePort;
import com.arton.backend.administer.banner.application.out.BannerRegistPort;
import com.arton.backend.administer.banner.application.out.BannerSelectAllPort;
import com.arton.backend.administer.banner.application.out.BannerSelectOnePort;
import com.arton.backend.administer.banner.domain.BannerEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BannerPersistenceAdapter implements BannerRegistPort, BannerSelectOnePort, BannerSelectAllPort, BannerDeletePort {

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
    
}
