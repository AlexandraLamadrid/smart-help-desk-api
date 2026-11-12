package com.company.smarthelpdesk.domain.port.out;

import com.company.smarthelpdesk.domain.model.category.Category;

import com.company.smarthelpdesk.domain.valueobject.CategoryId;

import java.util.List;

import java.util.Optional;

public interface CategoryRepositoryPort {

    Category save(Category category);

    Optional<Category> findById(CategoryId id);

    List<Category> findAll();

    boolean existsByNameIgnoreCase(String name);

}
