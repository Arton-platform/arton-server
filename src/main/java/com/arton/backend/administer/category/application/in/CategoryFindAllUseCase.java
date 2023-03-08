package com.arton.backend.administer.category.application.in;

import java.util.List;

import com.arton.backend.administer.category.domain.dtos.CategoryDto;

public interface CategoryFindAllUseCase {

    public List<CategoryDto> findAll();
    
}
