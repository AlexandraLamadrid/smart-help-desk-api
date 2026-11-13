package com.company.smarthelpdesk.application.usecase;
import com.company.smarthelpdesk.application.exception.ResourceNotFoundException;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;
import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;
import com.company.smarthelpdesk.domain.valueobject.CategoryId;
import com.company.smarthelpdesk.domain.valueobject.TicketId;
import com.company.smarthelpdesk.domain.valueobject.UserId;
import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryTicketRepositoryAdapter;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class GetTicketByIdServiceTest {
    @Test
    void shouldFindExistingTicket() {
        var repository =
                new InMemoryTicketRepositoryAdapter();
        var service =
                new GetTicketByIdService(repository);
        UUID ticketId = UUID.randomUUID();
        Ticket ticket = Ticket.open(
                new TicketId(ticketId),
                new UserId(UUID.randomUUID()),
                new CategoryId(UUID.randomUUID()),
                "Computador no enciende",
                "El computador no responde",
                TicketPriority.HIGH
        );
        repository.save(ticket);
        Ticket result = service.execute(ticketId);
        assertEquals(ticketId, result.getId().value());
        assertEquals("Computador no enciende", result.getSubject());
        assertEquals(TicketStatus.OPEN, result.getStatus());
    }
    @Test
    void shouldRejectNonExistingTicket() {
        var repository =
                new InMemoryTicketRepositoryAdapter();
        var service =
                new GetTicketByIdService(repository);
        UUID ticketId = UUID.randomUUID();
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.execute(ticketId)
        );
    }
    @Test
    void shouldRejectNullTicketId() {
        var repository =
                new InMemoryTicketRepositoryAdapter();
        var service =
                new GetTicketByIdService(repository);
        assertThrows(
                NullPointerException.class,
                () -> service.execute(null)
        );
    }
}
