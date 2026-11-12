package com.company.smarthelpdesk.application.query.dashboard;
import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;
import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;
import java.util.Map;
public record DashboardSummary(
        long totalTickets,
        Map<TicketStatus, Long> ticketsByStatus,
        Map<TicketPriority, Long> ticketsByPriority,
        double closedPercentage
) {
    public DashboardSummary {
        ticketsByStatus = Map.copyOf(ticketsByStatus);
        ticketsByPriority = Map.copyOf(ticketsByPriority);
    }
}