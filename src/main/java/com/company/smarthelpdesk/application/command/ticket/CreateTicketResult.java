package com.company.smarthelpdesk.application.command.ticket;
import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;
import java.util.UUID;
public record CreateTicketResult(
        UUID id,
        String subject,
        TicketStatus status
) {
}