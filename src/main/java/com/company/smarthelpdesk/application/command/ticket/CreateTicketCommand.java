package com.company.smarthelpdesk.application.command.ticket;
import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;
import java.util.UUID;
public record CreateTicketCommand(
        UUID userId,
        UUID categoryId,
        String subject,
        String description,
        TicketPriority priority
) {
}