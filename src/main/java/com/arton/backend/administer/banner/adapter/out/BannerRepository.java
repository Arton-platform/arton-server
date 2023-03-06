package com.arton.backend.administer.banner.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arton.backend.administer.banner.domain.BannerEntity;

public interface BannerRepository extends JpaRepository<BannerEntity,Long> {
    
}
