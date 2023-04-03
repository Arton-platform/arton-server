package com.arton.backend.administer.popup.domain;

import org.springframework.stereotype.Component;

@Component
public class PopUpMapper {
    public PopUpDto toDomain(PopUpEntity entity){
        return PopUpDto.builder()
            .starTime(entity.getStarTime())
            .endTime(entity.getEndTime())
            .isActive(entity.isActive())
            .positionX(entity.getPositionX())
            .positionY(entity.getPositionY())
            .size(entity.getSize())
            .url(entity.getUrl())
            .content(entity.getContent())
            .build();
    }

    public PopUpEntity toEntity(PopUpDto dto){
        return PopUpEntity.builder()
            .starTime(dto.getStarTime())
            .endTime(dto.getEndTime())
            .isActive(dto.isActive())
            .positionX(dto.getPositionX())
            .positionY(dto.getPositionY())
            .size(dto.getSize())
            .url(dto.getUrl())
            .content(dto.getContent())
            .build();
    }
}
