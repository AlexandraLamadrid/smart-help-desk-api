package com.company.smarthelpdesk.application.usecase;
import com.company.smarthelpdesk.application.command.ticket.CreateTicketCommand;
import com.company.smarthelpdesk.application.command.ticket.CreateTicketResult;
public interface CreateTicketUseCase {
    CreateTicketResult execute(CreateTicketCommand command);
}