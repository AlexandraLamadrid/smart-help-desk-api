package com.company.smarthelpdesk.infrastructure.adapters.persistence.memory;

import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryUserRepositoryAdapter;

import com.company.smarthelpdesk.domain.model.user.User;

import com.company.smarthelpdesk.domain.valueobject.EmailAddress;

import com.company.smarthelpdesk.domain.valueobject.FullName;

import com.company.smarthelpdesk.domain.valueobject.UserId;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryAdapterTest {

    private User createUser() {

        return new User(

                new UserId(UUID.randomUUID()),

                new FullName("Ana Maria Lopez"),

                new EmailAddress("ana@gmail.com")

        );

    }

    @Test

    void shouldSaveAndFindUserById() {

        var repository = new com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryUserRepositoryAdapter();

        User user = createUser();

        repository.save(user);

        assertTrue(repository.findById(user.getId()).isPresent());

    }

    @Test

    void shouldFindUserByEmail() {

        var repository = new InMemoryUserRepositoryAdapter();

        User user = createUser();

        repository.save(user);

        assertTrue(

                repository.findByEmail(user.getEmail()).isPresent()

        );

    }

    @Test

    void shouldReturnEmptyForUnknownUser() {

        var repository = new InMemoryUserRepositoryAdapter();

        assertTrue(

                repository.findById(

                        new UserId(UUID.randomUUID())

                ).isEmpty()

        );

    }

    @Test

    void shouldDetectExistingEmail() {

        var repository = new InMemoryUserRepositoryAdapter();

        User user = createUser();

        repository.save(user);

        assertTrue(repository.existsByEmail(user.getEmail()));

    }

}
