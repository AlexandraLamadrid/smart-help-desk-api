package com.company.smarthelpdesk.infrastructure.adapters.persistence.memory;

import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryCategoryRepositoryAdapter;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import com.company.smarthelpdesk.domain.model.category.Category;
import com.company.smarthelpdesk.domain.valueobject.CategoryId;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class InMemoryCategoryRepositoryAdapterTest {
    private Category createCategory(String name) {
        return new Category(
                new CategoryId(UUID.randomUUID()),
                name
        );
    }
    @Test
    void shouldSaveCategory() {
        var repository = new InMemoryCategoryRepositoryAdapter();
        Category category = createCategory("Hardware");
        repository.save(category);
        assertTrue(
                repository.findById(category.getId()).isPresent()
        );
    }
    @Test
    void shouldRejectDuplicateCategoryName() {
        var repository = new InMemoryCategoryRepositoryAdapter();
        repository.save(createCategory("Hardware"));
        assertThrows(
                InvalidValueException.class,
                () -> repository.save(createCategory("hardware"))
        );
    }
    @Test
    void shouldDetectNameIgnoringCase() {
        var repository = new InMemoryCategoryRepositoryAdapter();
        repository.save(createCategory("Software"));
        assertTrue(
                repository.existsByNameIgnoreCase("SOFTWARE")
        );
    }
    @Test
    void shouldReturnAllCategories() {
        var repository = new InMemoryCategoryRepositoryAdapter();
        repository.save(createCategory("Hardware"));
        repository.save(createCategory("Software"));
        assertEquals(2, repository.findAll().size());
    }
    @Test
    void shouldRejectEmptyCategoryName() {
        assertThrows(
                InvalidValueException.class,
                () -> createCategory("   ")
        );
    }
    @Test
    void shouldPreventModificationOfReturnedList() {
        var repository = new InMemoryCategoryRepositoryAdapter();
        repository.save(createCategory("Redes"));
        assertThrows(
                UnsupportedOperationException.class,
                () -> repository.findAll()
                        .add(createCategory("Hardware"))
        );
    }
}
