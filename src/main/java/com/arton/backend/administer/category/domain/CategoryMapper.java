package com.arton.backend.administer.category.domain;

import org.springframework.stereotype.Component;

import com.arton.backend.administer.category.domain.dtos.CategoryDto;

@Component
public class CategoryMapper<T> {

    public CategoryDto toDomain(CategoryEntity entity){
        return CategoryDto.builder()
        .id(entity.getId())
        .level(entity.getLevel())
        .lines(entity.getLines())
        .width(entity.getWidth())
        .url(entity.getUrl())
        .build();
    }

    public CategoryEntity toEntity(T dto){
        return CategoryEntity.builder()
        .url(((CategoryDto) dto).getUrl())
        .lines(((CategoryDto) dto).getLines())
        .level(((CategoryDto) dto).getLevel())
        .width(((CategoryDto) dto).getLines())
        .build();
    }

}
