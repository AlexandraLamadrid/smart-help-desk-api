package com.company.smarthelpdesk.infrastructure.adapters.persistence.memory;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;
import com.company.smarthelpdesk.domain.valueobject.TicketId;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
public final class InMemoryTicketRepositoryAdapter
        implements TicketRepositoryPort {
    private final ConcurrentMap<TicketId, Ticket> tickets =
            new ConcurrentHashMap<>();
    @Override
    public Ticket save(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Ticket is required"
            );
        }
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }
    @Override
    public Optional<Ticket> findById(TicketId id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(tickets.get(id));
    }
    @Override
    public List<Ticket> findAll() {
        return List.copyOf(tickets.values());
    }
}
