package com.arton.backend.administer.category.application.out;

import java.util.List;
import java.util.Optional;

import com.arton.backend.administer.category.domain.CategoryEntity;

public interface CategoryFindAllPort {

    Optional<List<CategoryEntity>> findAll();

}
