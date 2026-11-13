package com.company.smarthelpdesk.application.usecase;

import com.company.smarthelpdesk.application.command.ticket.CreateTicketCommand;

import com.company.smarthelpdesk.application.exception.ResourceNotFoundException;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import com.company.smarthelpdesk.domain.model.category.Category;

import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;

import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;

import com.company.smarthelpdesk.domain.model.user.User;

import com.company.smarthelpdesk.domain.valueobject.*;

import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.*;

import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryCategoryRepositoryAdapter;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryTicketRepositoryAdapter;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryUserRepositoryAdapter;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateTicketServiceTest {

    @Test

    void shouldCreateTicketSuccessfully() {

        var ticketRepository =

                new InMemoryTicketRepositoryAdapter();

        var userRepository =

                new InMemoryUserRepositoryAdapter();

        var categoryRepository =

                new InMemoryCategoryRepositoryAdapter();

        UUID userId = UUID.randomUUID();

        UUID categoryId = UUID.randomUUID();

        User user = new User(

                new UserId(userId),

                new FullName("Ana Maria Lopez"),

                new EmailAddress("ana@example.com")

        );

        Category category = new Category(

                new CategoryId(categoryId),

                "Hardware"

        );

        userRepository.save(user);

        categoryRepository.save(category);

        var service = new CreateTicketService(

                ticketRepository,

                userRepository,

                categoryRepository

        );

        var command = new CreateTicketCommand(

                userId,

                categoryId,

                "Computador no enciende",

                "El computador no responde al encenderlo",

                TicketPriority.HIGH

        );

        var result = service.execute(command);

        assertNotNull(result.id());

        assertEquals("Computador no enciende", result.subject());

        assertEquals(TicketStatus.OPEN, result.status());

        assertEquals(1, ticketRepository.findAll().size());

    }

    @Test
    void shouldRejectTicketWhenUserDoesNotExist() {
        var ticketRepository =
                new InMemoryTicketRepositoryAdapter();
        var userRepository =
                new InMemoryUserRepositoryAdapter();
        var categoryRepository =
                new InMemoryCategoryRepositoryAdapter();
        UUID categoryId = UUID.randomUUID();
        categoryRepository.save(
                new Category(
                        new CategoryId(categoryId),
                        "Hardware"
                )
        );
        var service = new CreateTicketService(
                ticketRepository,
                userRepository,
                categoryRepository
        );
        var command = new CreateTicketCommand(
                UUID.randomUUID(),
                categoryId,
                "Computador no enciende",
                "No responde al encenderlo",
                TicketPriority.HIGH
        );
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.execute(command)
        );
        assertTrue(ticketRepository.findAll().isEmpty());
    }

    @Test
    void shouldRejectTicketWhenCategoryDoesNotExist() {
        var ticketRepository =
                new InMemoryTicketRepositoryAdapter();
        var userRepository =
                new InMemoryUserRepositoryAdapter();
        var categoryRepository =
                new InMemoryCategoryRepositoryAdapter();
        UUID userId = UUID.randomUUID();
        userRepository.save(
                new User(
                        new UserId(userId),
                        new FullName("Ana Maria Lopez"),
                        new EmailAddress("ana@example.com")
                )
        );
        var service = new CreateTicketService(
                ticketRepository,
                userRepository,
                categoryRepository
        );
        var command = new CreateTicketCommand(
                userId,
                UUID.randomUUID(),
                "Problema con internet",
                "No tengo conexión a la red",
                TicketPriority.HIGH
        );
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.execute(command)
        );
        assertTrue(ticketRepository.findAll().isEmpty());
    }

    @Test

    void shouldRejectNullCommand() {

        var ticketRepository =

                new InMemoryTicketRepositoryAdapter();

        var userRepository =

                new InMemoryUserRepositoryAdapter();

        var categoryRepository =

                new InMemoryCategoryRepositoryAdapter();

        var service = new CreateTicketService(

                ticketRepository,

                userRepository,

                categoryRepository

        );

        assertThrows(

                NullPointerException.class,

                () -> service.execute(null)

        );

        assertTrue(ticketRepository.findAll().isEmpty());

    }

    @Test

    void shouldRejectEmptySubject() {

        var ticketRepository =

                new InMemoryTicketRepositoryAdapter();

        var userRepository =

                new InMemoryUserRepositoryAdapter();

        var categoryRepository =

                new InMemoryCategoryRepositoryAdapter();

        UUID userId = UUID.randomUUID();

        UUID categoryId = UUID.randomUUID();

        userRepository.save(

                new User(

                        new UserId(userId),

                        new FullName("Ana Maria Lopez"),

                        new EmailAddress("ana@example.com")

                )

        );

        categoryRepository.save(

                new Category(

                        new CategoryId(categoryId),

                        "Hardware"

                )

        );

        var service = new CreateTicketService(

                ticketRepository,

                userRepository,

                categoryRepository

        );

        var command = new CreateTicketCommand(

                userId,

                categoryId,

                "",

                "El computador no enciende",

                TicketPriority.HIGH

        );

        assertThrows(

                InvalidValueException.class,

                () -> service.execute(command)

        );

        assertTrue(ticketRepository.findAll().isEmpty());

    }

    @Test
    void shouldRejectEmptyDescription() {
        var ticketRepository =
                new InMemoryTicketRepositoryAdapter();
        var userRepository =
                new InMemoryUserRepositoryAdapter();
        var categoryRepository =
                new InMemoryCategoryRepositoryAdapter();
        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        userRepository.save(
                new User(
                        new UserId(userId),
                        new FullName("Ana Maria Lopez"),
                        new EmailAddress("ana@example.com")
                )
        );
        categoryRepository.save(
                new Category(
                        new CategoryId(categoryId),
                        "Hardware"
                )
        );
        var service = new CreateTicketService(
                ticketRepository,
                userRepository,
                categoryRepository
        );
        var command = new CreateTicketCommand(
                userId,
                categoryId,
                "Computador no enciende",
                "",
                TicketPriority.HIGH
        );
        assertThrows(
                InvalidValueException.class,
                () -> service.execute(command)
        );
        assertTrue(ticketRepository.findAll().isEmpty());
    }

    @Test
    void shouldRejectNullPriority() {
        var ticketRepository =
                new InMemoryTicketRepositoryAdapter();
        var userRepository =
                new InMemoryUserRepositoryAdapter();
        var categoryRepository =
                new InMemoryCategoryRepositoryAdapter();
        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        userRepository.save(
                new User(
                        new UserId(userId),
                        new FullName("Ana Maria Lopez"),
                        new EmailAddress("ana@example.com")
                )
        );
        categoryRepository.save(
                new Category(
                        new CategoryId(categoryId),
                        "Hardware"
                )
        );
        var service = new CreateTicketService(
                ticketRepository,
                userRepository,
                categoryRepository
        );
        var command = new CreateTicketCommand(
                userId,
                categoryId,
                "Problema de conexión",
                "No puedo conectarme a internet",
                null
        );
        assertThrows(
                InvalidValueException.class,
                () -> service.execute(command)
        );
        assertTrue(ticketRepository.findAll().isEmpty());
    }

}
