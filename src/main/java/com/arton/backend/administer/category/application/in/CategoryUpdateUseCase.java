package com.arton.backend.administer.category.application.in;

import com.arton.backend.administer.category.domain.dtos.CategoryUpdateDto;

public interface CategoryUpdateUseCase {

    public void update(CategoryUpdateDto updateDto);
    
}
