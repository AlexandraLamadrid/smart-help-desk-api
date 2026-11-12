package com.company.smarthelpdesk.domain.model.category;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import com.company.smarthelpdesk.domain.valueobject.CategoryId;
public final class Category {
    private final CategoryId id;
    private final String name;
    public Category(CategoryId id, String name) {
        if (id == null) {
            throw new InvalidValueException(
                    "Category ID is required"
            );
        }
        if (name == null || name.isBlank()) {
            throw new InvalidValueException(
                    "Category name is required"
            );
        }
        String normalizedName = name.trim()
                .replaceAll("\\s+", " ");
        if (normalizedName.length() > 100) {
            throw new InvalidValueException(
                    "Category name is too long"
            );
        }
        this.id = id;
        this.name = normalizedName;
    }
    public CategoryId getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
