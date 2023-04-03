package com.arton.backend.administer.category.application.in;

import com.arton.backend.administer.category.domain.dtos.CategoryDto;

public interface CategoryFindOneUseCase {

    public CategoryDto findOne(Long id);
    
}
