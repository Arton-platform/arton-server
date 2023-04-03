package com.arton.backend.administer.category.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arton.backend.administer.category.domain.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>{
    
}
