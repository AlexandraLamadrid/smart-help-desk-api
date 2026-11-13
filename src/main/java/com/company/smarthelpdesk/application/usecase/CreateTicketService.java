package com.company.smarthelpdesk.application.usecase;

import com.company.smarthelpdesk.application.command.ticket.CreateTicketCommand;

import com.company.smarthelpdesk.application.command.ticket.CreateTicketResult;

import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;

import com.company.smarthelpdesk.domain.port.out.UserRepositoryPort;

import com.company.smarthelpdesk.domain.port.out.CategoryRepositoryPort;

import java.util.Objects;

import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.valueobject.TicketId;
import com.company.smarthelpdesk.domain.valueobject.UserId;
import com.company.smarthelpdesk.domain.valueobject.CategoryId;
import com.company.smarthelpdesk.application.exception.ResourceNotFoundException;
import java.util.UUID;

public class CreateTicketService implements CreateTicketUseCase {

    private final TicketRepositoryPort ticketRepository;

    private final UserRepositoryPort userRepository;

    private final CategoryRepositoryPort categoryRepository;

    public CreateTicketService(

            TicketRepositoryPort ticketRepository,

            UserRepositoryPort userRepository,

            CategoryRepositoryPort categoryRepository

    ) {

        this.ticketRepository =

                Objects.requireNonNull(ticketRepository);

        this.userRepository =

                Objects.requireNonNull(userRepository);

        this.categoryRepository =

                Objects.requireNonNull(categoryRepository);

    }

    @Override

    public CreateTicketResult execute(CreateTicketCommand command) {

        Objects.requireNonNull(command, "Command is required");

        UserId userId = new UserId(command.userId());

        CategoryId categoryId = new CategoryId(command.categoryId());

        if (userRepository.findById(userId).isEmpty()) {

            throw new ResourceNotFoundException(

                    "User not found"

            );

        }

        if (categoryRepository.findById(categoryId).isEmpty()) {

            throw new ResourceNotFoundException(

                    "Category not found"

            );

        }

        Ticket ticket = Ticket.open(

                new TicketId(UUID.randomUUID()),

                userId,

                categoryId,

                command.subject(),

                command.description(),

                command.priority()

        );

        Ticket savedTicket = ticketRepository.save(ticket);

        return new CreateTicketResult(

                savedTicket.getId().value(),

                savedTicket.getSubject(),

                savedTicket.getStatus()

        );

    }


}
