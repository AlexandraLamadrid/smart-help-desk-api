package com.company.smarthelpdesk.domain.valueobject;
import com.company.smarthelpdesk.domain.exception.InvalidValueException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class EmailAddressTest {
    @Test
    void shouldNormalizeEmail() {
        EmailAddress email =
                new EmailAddress("  ALEX@GMAIL.COM  ");
        assertEquals("alex@gmail.com", email.value());
    }
    @Test
    void shouldRejectInvalidEmail() {
        assertThrows(
                InvalidValueException.class,
                () -> new EmailAddress("correo-invalido")
        );
    }
    @Test
    void shouldRejectNullEmail() {
        assertThrows(
                InvalidValueException.class,
                () -> new EmailAddress(null)
        );
    }
    @Test
    void shouldConsiderNormalizedEmailsEqual() {
        EmailAddress first =
                new EmailAddress("ALEX@GMAIL.COM");
        EmailAddress second =
                new EmailAddress("alex@gmail.com");
        assertEquals(first, second);
    }
}
