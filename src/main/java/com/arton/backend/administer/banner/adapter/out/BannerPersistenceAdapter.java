package com.arton.backend.administer.banner.adapter.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.arton.backend.administer.banner.application.out.*;
import org.springframework.stereotype.Repository;

import com.arton.backend.administer.banner.domain.BannerEntity;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;

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
    public List<BannerEntity> selectAllBanner() {
        
        return Optional.ofNullable(repository.findAll())
            .orElseGet(ArrayList::new);
    }

    @Override
    public BannerEntity selectOneBanner(long id) {
        return repository.findById(id)
            .orElseThrow(()-> new CustomException(ErrorCode.SELECT_ERROR.getMessage(),ErrorCode.SELECT_ERROR));
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
