package com.company.smarthelpdesk.application.query.dashboard;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;
import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;
import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
public final class GetDashboardSummaryUseCase {
    private final TicketRepositoryPort ticketRepository;
    public GetDashboardSummaryUseCase(
            TicketRepositoryPort ticketRepository
    ) {
        this.ticketRepository =
                Objects.requireNonNull(ticketRepository);
    }
    public DashboardSummary execute() {
        List<Ticket> tickets = ticketRepository.findAll();
        long totalTickets = tickets.size();
        Map<TicketStatus, Long> ticketsByStatus =
                tickets.stream()
                        .collect(Collectors.groupingBy(
                                Ticket::getStatus,
                                Collectors.counting()
                        ));
        Map<TicketPriority, Long> ticketsByPriority =
                tickets.stream()
                        .collect(Collectors.groupingBy(
                                Ticket::getPriority,
                                Collectors.counting()
                        ));
        long closedTickets = tickets.stream()
                .filter(ticket ->
                        ticket.getStatus() == TicketStatus.CLOSED)
                .count();
        double closedPercentage = totalTickets == 0
                ? 0.0
                : Math.round(
                (closedTickets * 100.0 / totalTickets)
                        * 100.0
        ) / 100.0;
        return new DashboardSummary(
                totalTickets,
                ticketsByStatus,
                ticketsByPriority,
                closedPercentage
        );
    }
}
