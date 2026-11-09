package com.company.smarthelpdesk.domain.valueobject;

import com.company.smarthelpdesk.domain.exception.InvalidValueException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FullNameTest {

    @Test

    void shouldNormalizeSpaces() {

        FullName name =

                new FullName("  Ana   Maria   Lopez  ");

        assertEquals("Ana Maria Lopez", name.value());

    }

    @Test

    void shouldRejectNullName() {

        assertThrows(

                InvalidValueException.class,

                () -> new FullName(null)

        );

    }

    @Test

    void shouldRejectShortName() {

        assertThrows(

                InvalidValueException.class,

                () -> new FullName("Al")

        );

    }

    @Test

    void shouldConsiderEqualNamesEquivalent() {

        FullName first = new FullName("Ana   Maria");

        FullName second = new FullName("Ana Maria");

        assertEquals(first, second);

    }

}
