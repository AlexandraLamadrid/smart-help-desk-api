package com.company.smarthelpdesk.domain.model.ticket;

import com.company.smarthelpdesk.domain.exception.InvalidValueException;

import com.company.smarthelpdesk.domain.valueobject.*;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    private Ticket createTicket() {

        return Ticket.open(

                TicketId.generate(),

                new UserId(UUID.randomUUID()),

                new CategoryId(UUID.randomUUID()),

                "Error de inicio de sesión",

                "No puedo ingresar al sistema",

                TicketPriority.MEDIUM

        );

    }

    @Test

    void shouldOpenTicketSuccessfully() {

        Ticket ticket = createTicket();

        assertNotNull(ticket.getId());

        assertEquals(TicketStatus.OPEN, ticket.getStatus());

        assertEquals(TicketPriority.MEDIUM,

                ticket.getPriority());

        assertNotNull(ticket.getCreatedAt());

        assertEquals(ticket.getCreatedAt(),

                ticket.getUpdatedAt());

    }

    @Test

    void shouldChangePriority() {

        Ticket ticket = createTicket();

        ticket.changePriority(TicketPriority.HIGH);

        assertEquals(TicketPriority.HIGH,

                ticket.getPriority());

    }

    @Test

    void shouldUpdateDescription() {

        Ticket ticket = createTicket();

        ticket.updateDescription(

                "El sistema muestra error 403"

        );

        assertEquals(

                "El sistema muestra error 403",

                ticket.getDescription()

        );

    }

    @Test

    void shouldRejectNullPriority() {

        Ticket ticket = createTicket();

        assertThrows(

                InvalidValueException.class,

                () -> ticket.changePriority(null)

        );

    }

    @Test

    void shouldRejectEmptyDescription() {

        Ticket ticket = createTicket();

        assertThrows(

                InvalidValueException.class,

                () -> ticket.updateDescription(" ")

        );

    }

    @Test

    void shouldRejectInvalidSubject() {

        assertThrows(

                InvalidValueException.class,

                () -> Ticket.open(

                        TicketId.generate(),

                        new UserId(UUID.randomUUID()),

                        new CategoryId(UUID.randomUUID()),

                        "",

                        "Descripción válida",

                        TicketPriority.LOW

                )

        );

    }

    @Test

    void shouldPreserveCreationDate() {

        Ticket ticket = createTicket();

        Instant createdAt = ticket.getCreatedAt();

        ticket.changePriority(TicketPriority.CRITICAL);

        assertEquals(createdAt, ticket.getCreatedAt());

        assertFalse(

                ticket.getUpdatedAt().isBefore(createdAt)

        );

    }

    @Test
    void shouldCloseTicket() {
        Ticket ticket = createTicket();
        ticket.close();
        assertEquals(
                TicketStatus.CLOSED,
                ticket.getStatus()
        );
    }

    @Test
    void shouldRejectPriorityChangeWhenClosed() {
        Ticket ticket = createTicket();
        ticket.close();
        assertThrows(
                InvalidValueException.class,
                () -> ticket.changePriority(
                        TicketPriority.HIGH
                )
        );
    }

    @Test
    void shouldRejectDescriptionUpdateWhenClosed() {
        Ticket ticket = createTicket();
        ticket.close();
        assertThrows(
                InvalidValueException.class,
                () -> ticket.updateDescription(
                        "Nueva descripción"
                )
        );
    }

    @Test
    void shouldRejectClosingAlreadyClosedTicket() {
        Ticket ticket = createTicket();
        ticket.close();
        assertThrows(
                InvalidValueException.class,
                ticket::close
        );
    }

}
