package com.arton.backend.administer.category.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.arton.backend.administer.category.application.out.CategoryDeletePort;
import com.arton.backend.administer.category.application.out.CategoryFindAllPort;
import com.arton.backend.administer.category.application.out.CategoryFindOnePort;
import com.arton.backend.administer.category.application.out.CategoryRegistPort;
import com.arton.backend.administer.category.domain.CategoryEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryAdapter implements CategoryFindOnePort, CategoryFindAllPort, CategoryRegistPort, CategoryDeletePort{

    private final CategoryRepository repository;


    @Override
    public Optional<CategoryEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<List<CategoryEntity>> findAll() {
        return Optional.ofNullable(repository.findAll());
    }

    @Override
    public void regist(CategoryEntity entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {

    }
}
