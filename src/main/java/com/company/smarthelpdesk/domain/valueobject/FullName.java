package com.company.smarthelpdesk.domain.valueobject;

import com.company.smarthelpdesk.domain.exception.InvalidValueException;

public record FullName(String value) {

    public FullName {

        if (value == null || value.isBlank()) {

            throw new InvalidValueException(

                    "Full name is required"

            );

        }

        value = value.trim().replaceAll("\\s+", " ");

        if (value.length() < 3 || value.length() > 120) {

            throw new InvalidValueException(

                    "Full name must be between 3 and 120 characters"

            );

        }

    }

}
