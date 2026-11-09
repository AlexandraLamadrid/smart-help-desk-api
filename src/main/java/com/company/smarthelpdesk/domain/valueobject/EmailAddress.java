package com.company.smarthelpdesk.domain.valueobject;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import java.util.Locale;
import java.util.regex.Pattern;
public record EmailAddress(String value) {
    private static final Pattern BASIC_EMAIL =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new InvalidValueException(
                    "Email is required"
            );
        }
        value = value.trim().toLowerCase(Locale.ROOT);
        if (!BASIC_EMAIL.matcher(value).matches()) {
            throw new InvalidValueException(
                    "Email format is invalid"
            );
        }
    }
}