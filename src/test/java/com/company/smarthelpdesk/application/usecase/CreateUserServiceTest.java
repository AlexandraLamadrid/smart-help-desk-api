package com.company.smarthelpdesk.application.usecase;

import com.company.smarthelpdesk.application.command.user.CreateUserCommand;

import com.company.smarthelpdesk.application.exception.ConflictException;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import com.company.smarthelpdesk.domain.model.user.RoleName;

import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryUserRepositoryAdapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserServiceTest {

    @Test

    void shouldCreateUserSuccessfully() {

        var repository =

                new InMemoryUserRepositoryAdapter();

        var service = new CreateUserService(repository);

        var command = new CreateUserCommand(

                "Ana Maria Lopez",

                "ana@example.com"

        );

        var result = service.execute(command);

        assertNotNull(result.id());

        assertEquals(

                "Ana Maria Lopez",

                result.fullName()

        );

        assertEquals(

                "ana@example.com",

                result.email()

        );

        var savedUser = repository.findByEmail(

                new com.company.smarthelpdesk.domain.valueobject.EmailAddress(

                        "ana@example.com"

                )

        ).orElseThrow();

        assertTrue(

                savedUser.getRoles().contains(RoleName.USER)

        );

    }

    @Test
    void shouldRejectDuplicateEmail() {
        var repository =
                new InMemoryUserRepositoryAdapter();
        var service =
                new CreateUserService(repository);
        var firstCommand = new CreateUserCommand(
                "Ana Maria Lopez",
                "ana@example.com"
        );
        var secondCommand = new CreateUserCommand(
                "Carlos Perez",
                "ana@example.com"
        );
        service.execute(firstCommand);
        assertThrows(
                ConflictException.class,
                () -> service.execute(secondCommand)
        );
    }

    @Test
    void shouldRejectInvalidEmail() {
        var repository =
                new InMemoryUserRepositoryAdapter();
        var service =
                new CreateUserService(repository);
        var command = new CreateUserCommand(
                "Ana Maria Lopez",
                "correo-invalido"
        );
        assertThrows(
                InvalidValueException.class,
                () -> service.execute(command)
        );
    }

    @Test
    void shouldRejectNullCommand() {
        var repository =
                new InMemoryUserRepositoryAdapter();
        var service =
                new CreateUserService(repository);
        assertThrows(
                NullPointerException.class,
                () -> service.execute(null)
        );
    }
}