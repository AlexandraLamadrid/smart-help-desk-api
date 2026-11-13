package com.company.smarthelpdesk.application.usecase;
import com.company.smarthelpdesk.application.exception.ResourceNotFoundException;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;
import com.company.smarthelpdesk.domain.valueobject.TicketId;
import java.util.Objects;
import java.util.UUID;
public class GetTicketByIdService implements GetTicketByIdUseCase {
    private final TicketRepositoryPort ticketRepository;
    public GetTicketByIdService(
            TicketRepositoryPort ticketRepository
    ) {
        this.ticketRepository =
                Objects.requireNonNull(ticketRepository);
    }
    @Override
    public Ticket execute(UUID ticketId) {
        Objects.requireNonNull(
                ticketId,
                "Ticket ID is required"
        );
        TicketId id = new TicketId(ticketId);
        return ticketRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Ticket not found"
                        )
                );
    }
}