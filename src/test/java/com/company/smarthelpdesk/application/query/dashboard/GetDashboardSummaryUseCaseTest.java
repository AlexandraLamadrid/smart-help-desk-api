package com.company.smarthelpdesk.application.query.dashboard;

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

class GetDashboardSummaryUseCaseTest {

    private Ticket createTicket(TicketPriority priority) {

        return Ticket.open(

                TicketId.generate(),

                new UserId(UUID.randomUUID()),

                new CategoryId(UUID.randomUUID()),

                "Problema técnico",

                "Necesito soporte",

                priority

        );

    }

    @Test

    void shouldReturnZeroForEmptyDashboard() {

        var repository =

                new InMemoryTicketRepositoryAdapter();

        var useCase =

                new GetDashboardSummaryUseCase(repository);

        DashboardSummary summary = useCase.execute();

        assertEquals(0, summary.totalTickets());

        assertEquals(0.0, summary.closedPercentage());

        assertTrue(summary.ticketsByStatus().isEmpty());

        assertTrue(summary.ticketsByPriority().isEmpty());

    }

    @Test

    void shouldCountTicketsByPriority() {

        var repository =

                new InMemoryTicketRepositoryAdapter();

        repository.save(createTicket(TicketPriority.HIGH));

        repository.save(createTicket(TicketPriority.HIGH));

        repository.save(createTicket(TicketPriority.LOW));

        var useCase =

                new GetDashboardSummaryUseCase(repository);

        DashboardSummary summary = useCase.execute();

        assertEquals(3, summary.totalTickets());

        assertEquals(

                2L,

                summary.ticketsByPriority().get(TicketPriority.HIGH)

        );

        assertEquals(

                1L,

                summary.ticketsByPriority().get(TicketPriority.LOW)

        );

    }

    @Test

    void shouldCountTicketsByStatus() {

        var repository =

                new InMemoryTicketRepositoryAdapter();

        Ticket openTicket =

                createTicket(TicketPriority.MEDIUM);

        Ticket closedTicket =

                createTicket(TicketPriority.HIGH);

        closedTicket.close();

        repository.save(openTicket);

        repository.save(closedTicket);

        var useCase =

                new GetDashboardSummaryUseCase(repository);

        DashboardSummary summary = useCase.execute();

        assertEquals(2, summary.totalTickets());

        assertEquals(

                1L,

                summary.ticketsByStatus().get(TicketStatus.OPEN)

        );

        assertEquals(

                1L,

                summary.ticketsByStatus().get(TicketStatus.CLOSED)

        );

    }

    @Test

    void shouldCalculateClosedPercentage() {

        var repository =

                new InMemoryTicketRepositoryAdapter();

        Ticket first =

                createTicket(TicketPriority.HIGH);

        Ticket second =

                createTicket(TicketPriority.LOW);

        Ticket third =

                createTicket(TicketPriority.MEDIUM);

        first.close();

        repository.save(first);

        repository.save(second);

        repository.save(third);

        var useCase =

                new GetDashboardSummaryUseCase(repository);

        DashboardSummary summary = useCase.execute();

        assertEquals(

                33.33,

                summary.closedPercentage(),

                0.001

        );

    }

}
