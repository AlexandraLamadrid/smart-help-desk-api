package com.company.smarthelpdesk.application.usecase;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import java.util.UUID;
public interface GetTicketByIdUseCase {
    Ticket execute(UUID ticketId);
}