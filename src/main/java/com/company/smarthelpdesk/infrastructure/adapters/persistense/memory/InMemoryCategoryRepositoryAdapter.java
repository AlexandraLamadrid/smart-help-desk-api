package com.company.smarthelpdesk.infrastructure.adapters.persistence.memory;

import com.company.smarthelpdesk.domain.exception.InvalidValueException;

import com.company.smarthelpdesk.domain.model.category.Category;

import com.company.smarthelpdesk.domain.port.out.CategoryRepositoryPort;

import com.company.smarthelpdesk.domain.valueobject.CategoryId;

import java.util.ArrayList;

import java.util.List;

import java.util.Locale;

import java.util.Optional;

import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.ConcurrentMap;

public final class InMemoryCategoryRepositoryAdapter

        implements CategoryRepositoryPort {

    private final ConcurrentMap<CategoryId, Category> categories =

            new ConcurrentHashMap<>();

    @Override

    public synchronized Category save(Category category) {

        if (category == null) {

            throw new InvalidValueException(

                    "Category is required"

            );

        }

        String normalizedName = normalize(category.getName());

        boolean duplicate = categories.values()

                .stream()

                .anyMatch(existing ->

                        !existing.getId().equals(category.getId())
                                && normalize(existing.getName())

                                .equals(normalizedName)

                );

        if (duplicate) {

            throw new InvalidValueException(

                    "Category name already exists"

            );

        }

        categories.put(category.getId(), category);

        return category;

    }

    @Override

    public Optional<Category> findById(CategoryId id) {

        if (id == null) {

            return Optional.empty();

        }

        return Optional.ofNullable(categories.get(id));

    }

    @Override

    public List<Category> findAll() {

        return List.copyOf(

                new ArrayList<>(categories.values())

        );

    }

    @Override

    public boolean existsByNameIgnoreCase(String name) {

        if (name == null || name.isBlank()) {

            return false;

        }

        String normalizedName = normalize(name);

        return categories.values()

                .stream()

                .anyMatch(category ->

                        normalize(category.getName())

                                .equals(normalizedName)

                );

    }

    private static String normalize(String value) {

        return value.trim()

                .replaceAll("\\s+", " ")

                .toLowerCase(Locale.ROOT);

    }

}
