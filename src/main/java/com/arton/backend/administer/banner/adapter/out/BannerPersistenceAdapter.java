package com.arton.backend.administer.banner.adapter.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.arton.backend.administer.banner.application.out.BannerDeletePort;
import com.arton.backend.administer.banner.application.out.BannerRegistPort;
import com.arton.backend.administer.banner.application.out.BannerSelectAllPort;
import com.arton.backend.administer.banner.application.out.BannerSelectOnePort;
import com.arton.backend.administer.banner.domain.BannerEntity;
import com.arton.backend.administer.banner.domain.BannerMapper;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;

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
    
}
