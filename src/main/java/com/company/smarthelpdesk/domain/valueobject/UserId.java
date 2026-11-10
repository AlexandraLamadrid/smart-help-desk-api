package com.company.smarthelpdesk.domain.valueobject;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import java.util.UUID;
public record UserId(UUID value) {
    public UserId {
        if (value == null) {
            throw new InvalidValueException(
                    "User ID is required"
            );
        }
    }
}