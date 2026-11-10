package com.company.smarthelpdesk.domain.model.ticket;

import com.company.smarthelpdesk.domain.exception.InvalidValueException;

import com.company.smarthelpdesk.domain.valueobject.TicketId;

import com.company.smarthelpdesk.domain.valueobject.UserId;

import com.company.smarthelpdesk.domain.valueobject.CategoryId;

import java.time.Instant;

public final class Ticket {

    private final TicketId id;

    private final UserId requesterId;

    private final CategoryId categoryId;

    private String subject;

    private String description;

    private TicketPriority priority;

    private TicketStatus status;

    private final Instant createdAt;

    private Instant updatedAt;

    private Ticket(

            TicketId id,

            UserId requesterId,

            CategoryId categoryId,

            String subject,

            String description,

            TicketPriority priority

    ) {

        if (id == null || requesterId == null ||

                categoryId == null || priority == null) {

            throw new InvalidValueException(

                    "Ticket required fields cannot be null"

            );

        }
        this.id = id;

        this.requesterId = requesterId;

        this.categoryId = categoryId;

        this.subject = requireText(subject, "subject");

        this.description = requireText(description, "description");

        this.priority = priority;

        this.status = TicketStatus.OPEN;

        this.createdAt = Instant.now();

        this.updatedAt = this.createdAt;

    }

    public static Ticket open(

            TicketId id,

            UserId requesterId,

            CategoryId categoryId,

            String subject,

            String description,

            TicketPriority priority

    ) {

        return new Ticket(

                id, requesterId, categoryId,

                subject, description, priority

        );

    }

    public void close() {
        ensureNotClosed();
        this.status = TicketStatus.CLOSED;
        this.updatedAt = Instant.now();
    }

    public void changePriority(TicketPriority newPriority) {

        ensureNotClosed();

        if (newPriority == null) {

            throw new InvalidValueException(

                    "Priority is required"

            );

        }

        this.priority = newPriority;

        this.updatedAt = Instant.now();

    }

    public void updateDescription(String newDescription) {

        ensureNotClosed();

        this.description = requireText(

                newDescription, "description"

        );

        this.updatedAt = Instant.now();

    }

    private void ensureNotClosed() {

        if (status == TicketStatus.CLOSED) {

            throw new InvalidValueException(

                    "Closed tickets cannot be modified"

            );

        }

    }

    private static String requireText(

            String value, String field

    ) {

        if (value == null || value.isBlank()) {

            throw new InvalidValueException(

                    field + " is required"

            );

        }

        return value.trim();

    }

    public TicketId getId() {

        return id;

    }

    public UserId getRequesterId() {

        return requesterId;

    }

    public CategoryId getCategoryId() {

        return categoryId;

    }

    public String getSubject() {

        return subject;

    }

    public String getDescription() {

        return description;

    }

    public TicketPriority getPriority() {

        return priority;

    }

    public TicketStatus getStatus() {

        return status;

    }

    public Instant getCreatedAt() {

        return createdAt;

    }

    public Instant getUpdatedAt() {

        return updatedAt;

    }

}
