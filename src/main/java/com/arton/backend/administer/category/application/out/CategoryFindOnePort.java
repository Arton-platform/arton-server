package com.arton.backend.administer.category.application.out;

import java.util.Optional;

import com.arton.backend.administer.category.domain.CategoryEntity;

public interface CategoryFindOnePort {

    Optional<CategoryEntity> findById(Long id);

}
