package com.company.smarthelpdesk.domain.valueobject;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import java.util.UUID;
public record TicketId(UUID value) {
    public TicketId {
        if (value == null) {
            throw new InvalidValueException(
                    "Ticket ID is required"
            );
        }
    }
    public static TicketId generate() {
        return new TicketId(UUID.randomUUID());
    }
}