package com.company.smarthelpdesk.domain.valueobject;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import java.util.UUID;
public record CategoryId(UUID value) {
    public CategoryId {
        if (value == null) {
            throw new InvalidValueException(
                    "Category ID is required"
            );
        }
    }
}