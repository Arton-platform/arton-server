package com.arton.backend.administer.category.application.out;

import com.arton.backend.administer.category.domain.CategoryEntity;

public interface CategoryFindOnePort {

    CategoryEntity findById(Long id);

}
