package com.company.smarthelpdesk.infrastructure.adapters.persistence.memory;

import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryTicketRepositoryAdapter;

import com.company.smarthelpdesk.domain.model.ticket.Ticket;

import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;

import com.company.smarthelpdesk.domain.valueobject.CategoryId;

import com.company.smarthelpdesk.domain.valueobject.TicketId;

import com.company.smarthelpdesk.domain.valueobject.UserId;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTicketRepositoryAdapterTest {

    private Ticket createTicket() {

        return Ticket.open(

                TicketId.generate(),

                new UserId(UUID.randomUUID()),

                new CategoryId(UUID.randomUUID()),

                "Error de acceso",

                "No puedo ingresar al sistema",

                TicketPriority.HIGH

        );

    }

    @Test

    void shouldSaveAndFindTicket() {

        var repository =

                new InMemoryTicketRepositoryAdapter();

        Ticket ticket = createTicket();

        repository.save(ticket);

        assertEquals(

                ticket.getId(),

                repository.findById(ticket.getId())

                        .orElseThrow()

                        .getId()

        );

    }

    @Test

    void shouldReturnEmptyWhenTicketDoesNotExist() {

        var repository =

                new InMemoryTicketRepositoryAdapter();

        assertTrue(

                repository.findById(

                        TicketId.generate()

                ).isEmpty()

        );

    }

    @Test

    void shouldReturnAllTickets() {

        var repository =

                new InMemoryTicketRepositoryAdapter();

        repository.save(createTicket());

        repository.save(createTicket());

        assertEquals(2, repository.findAll().size());

    }

    @Test

    void shouldRejectNullTicket() {

        var repository =

                new InMemoryTicketRepositoryAdapter();

        assertThrows(

                IllegalArgumentException.class,

                () -> repository.save(null)

        );

    }

    @Test

    void shouldReturnUnmodifiableList() {

        var repository =

                new InMemoryTicketRepositoryAdapter();

        assertThrows(

                UnsupportedOperationException.class,

                () -> repository.findAll()

                        .add(createTicket())

        );

    }

}
