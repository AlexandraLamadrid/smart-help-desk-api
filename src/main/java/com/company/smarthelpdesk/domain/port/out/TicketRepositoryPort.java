package com.company.smarthelpdesk.domain.port.out;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.valueobject.TicketId;
import java.util.List;
import java.util.Optional;
public interface TicketRepositoryPort {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(TicketId id);
    List<Ticket> findAll();
}