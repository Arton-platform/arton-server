package com.arton.backend.administer.banner.domain;

import org.springframework.stereotype.Component;

import com.arton.backend.infra.shared.Board;

@Component
public class BannerMapper {
    public Banner toDomain(BannerEntity entity){
        Board board = new Board(
            entity.getUser(),
            entity.getHit(),
            entity.getImage(),
            entity.getCreatedDate(),
            entity.getUpdateDate()
        );

        return new Banner(
            board,
            entity.getId(),
            entity.getSeq(),
            entity.getTitle(),
            entity.getIsActive()
        );
    }

    public BannerEntity toEntity(Banner banner){
        Board board = new Board(
            banner.getUser(),
            banner.getHit(),
            banner.getImage(),
            banner.getCreatedDate(),
            banner.getUpdateDate()
        );

        return new BannerEntity(
            board,
            banner.getId(),
            banner.getSeq(),
            banner.getTitle(),
            banner.getIsActive()
        );
    }
}
