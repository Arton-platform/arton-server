package com.arton.backend.administer.category.application.out;

import java.util.List;

import com.arton.backend.administer.category.domain.CategoryEntity;

public interface CategoryFindAllPort {

    List<CategoryEntity> findAll();

}
