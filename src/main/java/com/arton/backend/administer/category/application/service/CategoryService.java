package com.arton.backend.administer.category.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arton.backend.administer.category.application.in.CategoryDeleteUseCase;
import com.arton.backend.administer.category.application.in.CategoryFindAllUseCase;
import com.arton.backend.administer.category.application.in.CategoryFindOneUseCase;
import com.arton.backend.administer.category.application.in.CategoryRegistUseCase;
import com.arton.backend.administer.category.application.in.CategoryUpdateUseCase;
import com.arton.backend.administer.category.application.out.CategoryDeletePort;
import com.arton.backend.administer.category.application.out.CategoryFindAllPort;
import com.arton.backend.administer.category.application.out.CategoryFindOnePort;
import com.arton.backend.administer.category.application.out.CategoryRegistPort;
import com.arton.backend.administer.category.domain.CategoryEntity;
import com.arton.backend.administer.category.domain.CategoryMapper;
import com.arton.backend.administer.category.domain.dtos.CategoryDto;
import com.arton.backend.administer.category.domain.dtos.CategoryRegistDto;
import com.arton.backend.administer.category.domain.dtos.CategoryUpdateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryFindOneUseCase, CategoryFindAllUseCase, CategoryRegistUseCase, CategoryUpdateUseCase, CategoryDeleteUseCase{
    
    private final CategoryFindOnePort findOnePort;
    private final CategoryFindAllPort findAllPort;
    private final CategoryRegistPort registPort;
    private final CategoryDeletePort deletePort;
    
    @Override
    public CategoryDto findOne(Long id) {
        return new CategoryMapper<CategoryDto>().toDomain(
            findOnePort.findById(id)
        );
    }

    @Override
    public List<CategoryDto> findAll() {

        return findAllPort.findAll()
            .orElseGet(ArrayList::new)
            .stream()
            .map((entity) -> new CategoryMapper<CategoryDto>().toDomain(entity))
            .collect(Collectors.toList());
    }

    @Override
    public void regist(CategoryRegistDto registDto) {
        CategoryEntity entity = new CategoryMapper<CategoryRegistDto>().toEntity(registDto);
        registPort.regist(entity);
    }

    @Override
    public void update(CategoryUpdateDto updateDto) {
        CategoryEntity original = findOnePort.findById(updateDto.getId());
        original.update(
            updateDto.getLevel(),
            updateDto.getLines(),
            updateDto.getWidth(),
            updateDto.getUrl()
        );
    }

    @Override
    public void delete(Long id) {
        deletePort.delete(id);
    }

}
